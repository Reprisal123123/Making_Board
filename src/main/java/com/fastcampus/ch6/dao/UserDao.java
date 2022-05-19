package com.fastcampus.ch6.dao;

import com.fastcampus.ch6.domain.UserDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao {

    @Autowired
    SqlSession session;

    private String namespace = "com.fastcampus.ch6.dao.UserMapper.";

    public int insert(UserDto userDto) {
        return session.insert(namespace+"insert", userDto);
    }

    public UserDto select(String id) {
        return session.selectOne(namespace+"select", id);
    }



}
