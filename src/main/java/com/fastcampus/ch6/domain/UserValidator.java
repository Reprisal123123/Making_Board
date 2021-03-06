package com.fastcampus.ch6.domain;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class UserValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        // return UserDto.class.equals(clazz);
        return UserDto.class.isAssignableFrom(clazz);
    } // 타입이 올바른지 검사

    @Override
    public void validate(Object target, Errors errors) {
        UserDto userDto = (UserDto)target; // Dto 객체로 형변환
        String id = userDto.getId(); // Dto 객체에서 id 얻어오기

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "id", "required"); // 아이디가 비었을 때
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "pwd", "required"); // 비밀번호가 비었을 때

        if(id==null || id.length() < 8 || id.length() > 12) {
            errors.rejectValue("id", "invalidLength");
        } // 아이디 길이가 8~12가 아닐 때

    }
}
