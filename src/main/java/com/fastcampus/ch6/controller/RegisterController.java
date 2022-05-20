package com.fastcampus.ch6.controller;

import com.fastcampus.ch6.domain.UserDto;
import com.fastcampus.ch6.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;


@Controller
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    UserService userService;

    @InitBinder
    public void convert(WebDataBinder binder) {
        ConversionService conversionService = binder.getConversionService();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(df,false));
    }

    @GetMapping("/add")
    public String register() {
        return "registerForm";
    }

    @PostMapping("/add")
    public String save(@Valid UserDto userDto, BindingResult result, Model m) {

        try {
            if(!result.hasErrors() & userService.select(userDto.getId())==null) {
                userService.register(userDto);
                return "registerInfo";
            }
            throw new Exception("Register failed");
        } catch (Exception e) {
            e.printStackTrace();
            m.addAttribute("userDto", userDto);
            m.addAttribute("msg", "REG_ERR");
            return "registerForm";
        }

    }

}
