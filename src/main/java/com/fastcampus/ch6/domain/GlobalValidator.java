package com.fastcampus.ch6.domain;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class GlobalValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        // return UserDto.class.equals(clazz);
        return UserDto.class.isAssignableFrom(clazz);
    } // 타입이 올바른지 검사

    @Override
    public void validate(Object target, Errors errors) {
        UserDto userDto = (UserDto)target; // Dto 객체로 형변환
        String id = userDto.getId(); // Dto 객체에서 id 얻어오기
        String pwd = userDto.getPwd(); // 비밀번호 얻어오기

//        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "id", "required"); // 아이디가 비었을 때
//        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "pwd", "required"); // 비밀번호가 비었을 때
        if(id==null || id.equals("")) {
            errors.rejectValue("id", "required.user.id");
            return;
        }

        if(id.length() < 8 || id.length() > 12) {
            errors.rejectValue("id", "invalidLength.id", new String[]{"8","12"}, null);
        } // 아이디 길이가 8~12가 아닐 때

        if(pwd==null || pwd.equals("")) {
            errors.rejectValue("pwd", "required.user.pwd");
            return;
        }

        if(pwd.length() < 8 || pwd.length() > 12) {
            errors.rejectValue("id", "invalidLength.pwd", new String[]{"8","12"}, null);
        } // 아이디 길이가 8~12가 아닐 때
    }
}
