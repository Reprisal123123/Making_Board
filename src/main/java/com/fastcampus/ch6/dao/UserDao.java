package com.fastcampus.ch6.dao;

import com.fastcampus.ch6.domain.UserDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserDao {

    @Autowired
    SqlSession session;

    private String namespace = "com.fastcampus.ch6.dao.UserMapper.";

    public int insert(UserDto userDto) throws Exception {
        return session.insert(namespace+"insert", userDto);
    }

    public UserDto select(String id) throws Exception {
        return session.selectOne(namespace+"select", id);
    }

    public List<UserDto> selectAll() throws Exception {
        return session.selectList(namespace+"selectAll");
    }

    public int count() throws Exception {
        return session.selectOne(namespace+"count");
    }

    public int update(UserDto userDto) throws Exception {
        return session.update(namespace+"update", userDto);
    }

    public int delete(String id, String pwd) throws Exception {
        Map map = new HashMap();
        map.put("id", id);
        map.put("pwd", pwd);
        return session.delete(namespace+"delete", map);
    }

    public int deleteAll() throws Exception {
        return session.delete(namespace+"deleteAll");
    }

}
