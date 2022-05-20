package com.fastcampus.ch6.controller;

import com.fastcampus.ch6.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SimpleRestController {
    @Autowired
    UserService userService;

    @PostMapping("/idcheck")
    @ResponseBody
    public Boolean idcheck(@RequestBody String id) throws Exception {
        System.out.println("id = " + id);
        if((id.length() >= 8 & id.length() <= 12) & userService.select(id)==null) {
            return true;
        }
        return false;
    }
}
