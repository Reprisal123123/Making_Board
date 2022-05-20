package com.fastcampus.ch6.controller;

import com.fastcampus.ch6.domain.UserDto;
import com.fastcampus.ch6.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    UserService userService;

    @GetMapping("/add")
    public String register() {
        return "registerForm";
    }

    @PostMapping("/add")
    public String save(UserDto userDto, Model m) {

        try {
            userService.register(userDto);
        } catch (Exception e) {

        }

        return "registerInfo";

    }

}
