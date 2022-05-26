package com.fastcampus.ch6.dao;

import com.fastcampus.ch6.domain.BoardDto;
import com.fastcampus.ch6.domain.SearchCondition;
import com.fastcampus.ch6.domain.UserDto;

import java.util.List;
import java.util.Map;

public interface UserDao {
    int insert(UserDto userDto) throws Exception;
    // 아이디 생성
    
    UserDto select(String id) throws Exception;
    // 아이디 선택
    
    List<UserDto> selectAll() throws Exception;
    // 아이디 전부 선택
    
    int count() throws Exception;
    // 총 아이디 갯수
    
    int update(UserDto userDto) throws Exception;
    // 아이디 수정 업데이트
    
    int delete(String id, String pwd) throws Exception;
    // 아이디 삭제
    
    int deleteAll() throws Exception;
    // 아이디 전부 삭제

    UserDto loginCheck(String id, String pwd) throws Exception;

}
