package com.fastcampus.ch6.controller;

import com.fastcampus.ch6.domain.BoardDto;
import com.fastcampus.ch6.domain.PageHandler;
import com.fastcampus.ch6.domain.SearchCondition;
import com.fastcampus.ch6.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/board")
public class BoardController {
    @Autowired
    BoardService boardService;

    @GetMapping("/list")
    public String list(SearchCondition sc, Model m, HttpServletRequest request) {
        // 세션을 통해 로그인 여부 확인
        // 로그인 되어있지 않으면 toURL에 주소를 담아 전달
        if(!loginCheck(request))
            return "redirect:/login/login?toURL="+request.getRequestURL();

        // page와 pageSize가 null이면 기본값을 설정해준다
//        if(page==null) page = 1;
//        if(pageSize==null) pageSize = 10;

        try {
            int totalCnt = boardService.getSearchResultCnt(sc); // 총 게시물 숫자 얻기
            m.addAttribute("totalCnt", totalCnt);

            PageHandler pageHandler = new PageHandler(totalCnt, sc);
            // 페이지 핸들러 생성


            // 조건에 맞는 게시물 리스트 얻어와서 모델에 list와 페이지 핸들러를 저장
            List<BoardDto> list = boardService.getSearchResultPage(sc);
            m.addAttribute("list", list);
            m.addAttribute("ph", pageHandler);

            Instant startOfToday = LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant();
            m.addAttribute("startOfToday", startOfToday.toEpochMilli());
        } catch (Exception e) {
            e.printStackTrace();
            m.addAttribute("msg", "LIST_ERR");
            m.addAttribute("totalCnt", 0);
        }

        return "boardList";
    }

    @GetMapping("/write")
    public String write(Model m) {
        m.addAttribute("mode", "new");
        return "board"; // 읽기와 쓰기에 사용, 쓰기에 사용할 때는 mode=new
    }

    @PostMapping("/write")
    public String write(BoardDto boardDto, HttpSession session, RedirectAttributes rattr, Model m) {
        String writer = (String)session.getAttribute("id");
        boardDto.setWriter(writer);
        try {
            int rowCnt = boardService.write(boardDto);

            if(rowCnt!=1)
                throw new Exception("Write failed");

            rattr.addFlashAttribute("msg", "WRT_OK");

            return "redirect:/board/list";
        } catch (Exception e) {
            e.printStackTrace();
            m.addAttribute("boardDto", boardDto);
            m.addAttribute("msg", "WRT_ERR");
            return "board";
        }

    }

    @GetMapping("/read")
    public String read(Integer bno, SearchCondition sc, RedirectAttributes rattr, Model m) {
        try {
            BoardDto boardDto = boardService.read(bno);

            m.addAttribute("boardDto", boardDto);
            m.addAttribute("page", sc.getPage());
            m.addAttribute("pageSize", sc.getPageSize());
            m.addAttribute("searchCondition", sc);
        } catch (Exception e) {
            e.printStackTrace();
            rattr.addFlashAttribute("msg", "READ_ERR");
            return "redirect:/board/list"+sc.getQueryString();
        }

        return "board";
    }

    @PostMapping("/modify")
    public String modify(BoardDto boardDto, SearchCondition sc, HttpSession session, Model m, RedirectAttributes rattr) {
//        title bno content
        String writer = (String)session.getAttribute("id");
        boardDto.setWriter(writer);

        try {
            int rowCnt = boardService.modify(boardDto);
            if(rowCnt != 1) {
                throw new Exception("Modify failed");
            }

            rattr.addFlashAttribute("msg", "MOD_OK");
            rattr.addAttribute("page", sc.getPage());
            rattr.addAttribute("pageSize", sc.getPageSize());
            rattr.addAttribute("option", sc.getOption());
            rattr.addAttribute("keyword", sc.getKeyword());
            rattr.addAttribute("bno", boardDto.getBno());

            return "redirect:/board/read";
        } catch (Exception e) {
            e.printStackTrace();
            m.addAttribute("BoardDto", boardDto);
            m.addAttribute("msg","MOD_ERR");
            return "board";
        }

    }

    @PostMapping("/remove")
    public String remove(Integer bno, Integer page, Integer pageSize, Model m, HttpSession session, RedirectAttributes rattr) {
        String writer = (String)session.getAttribute("id");
        try {
            m.addAttribute("page", page);
            m.addAttribute("pageSize", pageSize);
            // 모델에 담으면 redirect할 때 쿼리 스트링이 ?page=x&pageSize=y 같은 식으로 붙게됨

            int rowCnt = boardService.remove(bno, writer);

            if(rowCnt==1) {
                // 한번만 에러메세지 띄우려면 RedirectAttributes 객체의
                // addFlashAttribute 메소드를 사용하면 된다(jsp 파일에 msg 출력부분 수정 필요)
                rattr.addFlashAttribute("msg", "DEL_OK");
                return "redirect:/board/list";
            } else {
                throw new Exception ("board remove error");
            }

        } catch (Exception e) {
            e.printStackTrace();
            rattr.addFlashAttribute("msg", "DEL_ERR");
        }

        return "redirect:/board/list";
    }



    private boolean loginCheck(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return session.getAttribute("id")!=null;
    }


}
