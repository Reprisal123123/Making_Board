package com.fastcampus.ch6.service;

import com.fastcampus.ch6.dao.BoardDao;
import com.fastcampus.ch6.domain.BoardDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class BoardService {

    @Autowired
    BoardDao boardDao;

    public int write(BoardDto boardDto) throws Exception {
        return boardDao.insert(boardDto);
    } // �� ����

    public BoardDto read(Integer bno) throws Exception {
        BoardDto boardDto = boardDao.select(bno);
        boardDao.increaseViewCnt(bno);

        return boardDto;
    } // �� �б�

    public List<BoardDto> getList() throws Exception {
        return boardDao.selectAll();
    } // ��� ���� list�� �� ��������

    public int getCount() throws Exception {
        return boardDao.count();
    } // ��ϵ� ���� ����

    public int modify(BoardDto boardDto) throws Exception {
        return boardDao.update(boardDto);
    } // �� �����ϱ�

    public int remove(Integer bno, String writer) throws Exception {
        return boardDao.delete(bno, writer);
    } // �� ����

    public List<BoardDto> getPage(Map map) throws Exception {
        return boardDao.selectPage(map);
    }
}
