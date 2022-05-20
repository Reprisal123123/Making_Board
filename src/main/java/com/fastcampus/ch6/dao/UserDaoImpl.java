package com.fastcampus.ch6.dao;

import com.fastcampus.ch6.domain.UserDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    SqlSession session; // Sql 세션 연결

    private String namespace = "com.fastcampus.ch6.dao.UserMapper.";

    @Override
    public int insert(UserDto userDto) throws Exception {
        return session.insert(namespace+"insert", userDto);
    } // 아이디 등록

    @Override
    public UserDto select(String id) throws Exception {
        return session.selectOne(namespace+"select", id);
    } // 아이디 선택

    @Override
    public List<UserDto> selectAll() throws Exception {
        return session.selectList(namespace+"selectAll");
    } // 모든 아이디 목록

    @Override
    public int count() throws Exception {
        return session.selectOne(namespace+"count");
    } // 모든 아이디 카운트

    @Override
    public int update(UserDto userDto) throws Exception {
        return session.update(namespace+"update", userDto);
    } // 아이디 업데이트

    @Override
    public int delete(String id, String pwd) throws Exception {
        Map map = new HashMap();
        map.put("id", id);
        map.put("pwd", pwd);
        return session.delete(namespace+"delete", map);
    } // 아이디 삭제

    @Override
    public int deleteAll() throws Exception {
        return session.delete(namespace+"deleteAll");
    } // 아이디 전부 삭제

}
