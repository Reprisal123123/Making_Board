package com.fastcampus.ch6.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/board")
public class BoardController {

    @GetMapping("/list")
    public String list(HttpServletRequest request) {
        // 세션을 통해 로그인 여부 확인
        // 로그인 되어있지 않으면 toURL에 주소를 담아 전달
        if(!loginCheck(request))
            return "redirect:/login/login?toURL="+request.getRequestURL();

        return "boardList";
    }

    private boolean loginCheck(HttpServletRequest request) {
        HttpSession session = request.getSession();
        return session.getAttribute("id")!=null;
    }


}
