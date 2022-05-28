package com.fastcampus.ch6.service;

import com.fastcampus.ch6.domain.BoardDto;
import com.fastcampus.ch6.domain.SearchCondition;

import java.util.List;
import java.util.Map;

public interface BoardService {
    int write(BoardDto boardDto) throws Exception // 글 쓰기
    ;

    BoardDto read(Integer bno) throws Exception // 글 읽기
    ;

    List<BoardDto> getList() throws Exception // 모든 글을 list로 다 가져오기
    ;

    int getCount() throws Exception // 등록된 글의 갯수
    ;

    int modify(BoardDto boardDto) throws Exception // 글 수정하기
    ;

    int remove(Integer bno, String writer) throws Exception // 글 삭제
    ;

    List<BoardDto> getPage(Map map) throws Exception;

    int getSearchResultCnt(SearchCondition sc) throws Exception;

    List<BoardDto> getSearchResultPage(SearchCondition sc) throws Exception;
}
