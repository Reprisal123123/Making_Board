package com.fastcampus.ch6.service;

import com.fastcampus.ch6.dao.UserDao;
import com.fastcampus.ch6.domain.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserDao userDao;

    public int register(UserDto userDto) throws Exception {
        String id = userDto.getId();
        String pwd = userDto.getPwd();

        return userDao.insert(userDto);
    } // 아이디 등록

    public UserDto select(String id) throws Exception {
        return userDao.select(id);
    } // 아이디 선택

}
