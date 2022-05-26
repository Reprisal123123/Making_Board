package com.fastcampus.ch6.controller;

import com.fastcampus.ch6.domain.BoardDto;
import com.fastcampus.ch6.domain.PageHandler;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/board")
public class BoardController {
    @Autowired
    BoardService boardService;

    @GetMapping("/list")
    public String list(Integer page, Integer pageSize, Model m, HttpServletRequest request) {
        // 세션을 통해 로그인 여부 확인
        // 로그인 되어있지 않으면 toURL에 주소를 담아 전달
        if(!loginCheck(request))
            return "redirect:/login/login?toURL="+request.getRequestURL();

        // page와 pageSize가 null이면 기본값을 설정해준다
        if(page==null) page = 1;
        if(pageSize==null) pageSize = 10;

        try {
            int totalCnt = boardService.getCount(); // 총 게시물 숫자 얻기
            PageHandler pageHandler = new PageHandler(totalCnt, page, pageSize);
            // 페이지 핸들러 생성

            // offset과 pageSize를 map에 저장
            Map map = new HashMap();
            map.put("offset", (page-1) * pageSize);
            map.put("pageSize", pageSize);

            // 조건에 맞는 게시물 리스트 얻어와서 모델에 list와 페이지 핸들러를 저장
            List<BoardDto> list = boardService.getPage(map);
            m.addAttribute("list", list);
            m.addAttribute("ph", pageHandler);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "boardList";
    }

    @GetMapping("/read")
    public String read(Integer bno, Integer page, Integer pageSize, Model m) {
        try {
            BoardDto boardDto = boardService.read(bno);

            m.addAttribute("boardDto", boardDto);
            m.addAttribute("page", page);
            m.addAttribute("pageSize", pageSize);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "board";
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
