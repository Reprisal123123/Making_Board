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
        // ������ ���� �α��� ���� Ȯ��
        // �α��� �Ǿ����� ������ toURL�� �ּҸ� ��� ����
        if(!loginCheck(request))
            return "redirect:/login/login?toURL="+request.getRequestURL();

        // page�� pageSize�� null�̸� �⺻���� �������ش�
        if(page==null) page = 1;
        if(pageSize==null) pageSize = 10;

        try {
            int totalCnt = boardService.getCount(); // �� �Խù� ���� ���
            PageHandler pageHandler = new PageHandler(totalCnt, page, pageSize);
            // ������ �ڵ鷯 ����

            // offset�� pageSize�� map�� ����
            Map map = new HashMap();
            map.put("offset", (page-1) * pageSize);
            map.put("pageSize", pageSize);

            // ���ǿ� �´� �Խù� ����Ʈ ���ͼ� �𵨿� list�� ������ �ڵ鷯�� ����
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
