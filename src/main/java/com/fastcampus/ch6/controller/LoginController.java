package com.fastcampus.ch6.controller;

import com.fastcampus.ch6.domain.UserDto;
import com.fastcampus.ch6.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URLEncoder;

@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    UserService userService;

    @GetMapping("/login")
    public String loginForm() {
        return "loginForm";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        // 1. 세션을 종료
        session.invalidate();
        // 2. 홈으로 이동
        return "redirect:/";
    }

    @PostMapping("/login")
    public String login(String id, String pwd, String toURL, boolean rememberId,
                        HttpServletRequest request, HttpServletResponse response) {

        try {
            // id와 pwd가 일치하면
            if(userService.loginCheck(id, pwd)!=null) {
                // 세션 객체를 얻어와서 id를 저장
                HttpSession session = request.getSession();
                session.setAttribute("id", id);

                // 아이디 기억에 체크 되어있으면
                if(rememberId) {
                    // 쿠키를 생성해서 응답에 저장
                    Cookie cookie = new Cookie("id", id);
                    response.addCookie(cookie);
                } else {
                    // 쿠키를 삭제
                    Cookie cookie = new Cookie("id", id);
                    cookie.setMaxAge(0); // 쿠키 삭제하는 코드
                    response.addCookie(cookie); // 응답에 저장
                }

                toURL = toURL == null || toURL.equals("") ? "/" : toURL;
                return "redirect:" + toURL;

            }

            String msg = URLEncoder.encode("id 또는 pwd가 일치하지 않습니다.", "utf-8");
            return "redirect:/login/login?msg="+msg;
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/login/login";
        }

    }
}
