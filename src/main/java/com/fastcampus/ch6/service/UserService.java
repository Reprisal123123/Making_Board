package com.fastcampus.ch6.service;

import com.fastcampus.ch6.domain.UserDto;

public interface UserService {
    int register(UserDto userDto) throws Exception // ���̵� ���
    ;

    UserDto select(String id) throws Exception // ���̵� ����
    ;

    UserDto loginCheck(String id, String pwd) throws Exception;
}
