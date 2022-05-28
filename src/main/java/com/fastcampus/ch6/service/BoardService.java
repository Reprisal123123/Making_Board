package com.fastcampus.ch6.service;

import com.fastcampus.ch6.domain.BoardDto;
import com.fastcampus.ch6.domain.SearchCondition;

import java.util.List;
import java.util.Map;

public interface BoardService {
    int write(BoardDto boardDto) throws Exception // �� ����
    ;

    BoardDto read(Integer bno) throws Exception // �� �б�
    ;

    List<BoardDto> getList() throws Exception // ��� ���� list�� �� ��������
    ;

    int getCount() throws Exception // ��ϵ� ���� ����
    ;

    int modify(BoardDto boardDto) throws Exception // �� �����ϱ�
    ;

    int remove(Integer bno, String writer) throws Exception // �� ����
    ;

    List<BoardDto> getPage(Map map) throws Exception;

    int getSearchResultCnt(SearchCondition sc) throws Exception;

    List<BoardDto> getSearchResultPage(SearchCondition sc) throws Exception;
}
