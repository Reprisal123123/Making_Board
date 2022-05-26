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

//        for(int i=0; i < id.length(); i++) {
//            if(id.charAt(i)
//        }

        if((id.length() >= 8 & id.length() <= 12) & userService.select(id)==null) {
            return true;
        }
        return false;
    }

    @PostMapping("/pwdcheck")
    @ResponseBody
    public Boolean pwdcheck(@RequestBody String pwd) throws Exception {
        System.out.println("pwd = " + pwd);
        if((pwd.length() >= 8 & pwd.length() <= 12)) {
            return true;
        }
        return false;
    }

//    private boolean isSl(String id) {
//        for(int i=0; i < id.length(); i++) {
//            if(id.charAt(i)
//        }
//    }
}
