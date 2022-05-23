package com.fastcampus.ch6.dao;

import com.fastcampus.ch6.domain.BoardDto;

import java.util.List;

public interface BoardDao {
    int insert(BoardDto boardDto) throws Exception;

    BoardDto select(Integer bno) throws Exception;

    List<BoardDto> selectAll() throws Exception;

    int count() throws Exception;

    int update(BoardDto boardDto) throws Exception;

    int increaseViewCnt(Integer bno) throws Exception;

    int delete(Integer bno, String writer) throws Exception;

    int deleteAll() throws Exception;
}
