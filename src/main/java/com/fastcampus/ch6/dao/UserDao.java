package com.fastcampus.ch6.dao;

import com.fastcampus.ch6.domain.UserDto;

import java.util.List;

public interface UserDao {
    int insert(UserDto userDto) throws Exception;

    UserDto select(String id) throws Exception;

    List<UserDto> selectAll() throws Exception;

    int count() throws Exception;

    int update(UserDto userDto) throws Exception;

    int delete(String id, String pwd) throws Exception;

    int deleteAll() throws Exception;
}
