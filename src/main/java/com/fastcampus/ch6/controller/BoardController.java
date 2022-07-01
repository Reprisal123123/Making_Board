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
        // ������ ���� �α��� ���� Ȯ��
        // �α��� �Ǿ����� ������ toURL�� �ּҸ� ��� ����
        if(!loginCheck(request))
            return "redirect:/login/login?toURL="+request.getRequestURL();

        // page�� pageSize�� null�̸� �⺻���� �������ش�
//        if(page==null) page = 1;
//        if(pageSize==null) pageSize = 10;

        try {
            int totalCnt = boardService.getSearchResultCnt(sc); // �� �Խù� ���� ���
            m.addAttribute("totalCnt", totalCnt);

            PageHandler pageHandler = new PageHandler(totalCnt, sc);
            // ������ �ڵ鷯 ����


            // ���ǿ� �´� �Խù� ����Ʈ ���ͼ� �𵨿� list�� ������ �ڵ鷯�� ����
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
        return "board"; // �б�� ���⿡ ���, ���⿡ ����� ���� mode=new
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
            // �𵨿� ������ redirect�� �� ���� ��Ʈ���� ?page=x&pageSize=y ���� ������ �ٰԵ�

            int rowCnt = boardService.remove(bno, writer);

            if(rowCnt==1) {
                // �ѹ��� �����޼��� ������ RedirectAttributes ��ü��
                // addFlashAttribute �޼ҵ带 ����ϸ� �ȴ�(jsp ���Ͽ� msg ��ºκ� ���� �ʿ�)
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
