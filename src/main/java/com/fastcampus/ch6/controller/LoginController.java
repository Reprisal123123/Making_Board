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
        // 1. ������ ����
        session.invalidate();
        // 2. Ȩ���� �̵�
        return "redirect:/";
    }

    @PostMapping("/login")
    public String login(String id, String pwd, String toURL, boolean rememberId,
                        HttpServletRequest request, HttpServletResponse response) {

        try {
            // id�� pwd�� ��ġ�ϸ�
            if(userService.loginCheck(id, pwd)!=null) {
                // ���� ��ü�� ���ͼ� id�� ����
                HttpSession session = request.getSession();
                session.setAttribute("id", id);

                // ���̵� ��￡ üũ �Ǿ�������
                if(rememberId) {
                    // ��Ű�� �����ؼ� ���信 ����
                    Cookie cookie = new Cookie("id", id);
                    response.addCookie(cookie);
                } else {
                    // ��Ű�� ����
                    Cookie cookie = new Cookie("id", id);
                    cookie.setMaxAge(0); // ��Ű �����ϴ� �ڵ�
                    response.addCookie(cookie); // ���信 ����
                }

                toURL = toURL == null || toURL.equals("") ? "/" : toURL;
                return "redirect:" + toURL;

            }

            String msg = URLEncoder.encode("id �Ǵ� pwd�� ��ġ���� �ʽ��ϴ�.", "utf-8");
            return "redirect:/login/login?msg="+msg;
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/login/login";
        }

    }
}
