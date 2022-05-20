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
        UserDto userDto = (UserDto)target;
        String id = userDto.getId();

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "id", "required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "pwd", "required");

        if(id==null || id.length() < 8 || id.length() > 12) {
            errors.rejectValue("id", "invalidLength");
        }

    }
}
