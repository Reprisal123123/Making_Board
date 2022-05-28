package com.fastcampus.ch6.service;

import com.fastcampus.ch6.dao.BoardDao;
import com.fastcampus.ch6.domain.BoardDto;
import com.fastcampus.ch6.domain.SearchCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class BoardServiceImpl implements BoardService {

    @Autowired
    BoardDao boardDao;

    @Override
    public int write(BoardDto boardDto) throws Exception {
        return boardDao.insert(boardDto);
    } // 글 쓰기

    @Override
    public BoardDto read(Integer bno) throws Exception {
        BoardDto boardDto = boardDao.select(bno);
        boardDao.increaseViewCnt(bno);

        return boardDto;
    } // 글 읽기

    @Override
    public List<BoardDto> getList() throws Exception {
        return boardDao.selectAll();
    } // 모든 글을 list로 다 가져오기

    @Override
    public int getCount() throws Exception {
        return boardDao.count();
    } // 등록된 글의 갯수

    @Override
    public int modify(BoardDto boardDto) throws Exception {
        return boardDao.update(boardDto);
    } // 글 수정하기

    @Override
    public int remove(Integer bno, String writer) throws Exception {
        return boardDao.delete(bno, writer);
    } // 글 삭제

    @Override
    public List<BoardDto> getPage(Map map) throws Exception {
        return boardDao.selectPage(map);
    }

    @Override
    public int getSearchResultCnt(SearchCondition sc) throws Exception {
        return boardDao.searchResultCnt(sc);
    }

    @Override
    public List<BoardDto> getSearchResultPage(SearchCondition sc) throws Exception {
        return boardDao.searchSelectPage(sc);
    }
}
