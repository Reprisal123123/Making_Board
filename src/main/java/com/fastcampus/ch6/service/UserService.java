package com.fastcampus.ch6.service;

import com.fastcampus.ch6.domain.UserDto;

public interface UserService {
    int register(UserDto userDto) throws Exception // 아이디 등록
    ;

    UserDto select(String id) throws Exception // 아이디 선택
    ;

    UserDto loginCheck(String id, String pwd) throws Exception;
}
