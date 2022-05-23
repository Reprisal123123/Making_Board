package com.fastcampus.ch6.dao;

import com.fastcampus.ch6.domain.BoardDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class BoardDaoImplTest {

    @Autowired
    private BoardDao boardDao;

    @Test
    public void insertTest() throws Exception {
        boardDao.deleteAll();
        assertTrue(boardDao.count()==0);

        BoardDto boardDto = new BoardDto("no title", "123123", "asdf");
        assertTrue(boardDao.insert(boardDto)==1);
        assertTrue(boardDao.count()==1);

        BoardDto boardDto2 = new BoardDto("no title2", "123123", "asdf");
        assertTrue(boardDao.insert(boardDto)==1);
        assertTrue(boardDao.count()==2);

    }

    @Test
    public void selectTest() throws Exception {
        boardDao.deleteAll();
        assertTrue(boardDao.count()==0);

        BoardDto boardDto = new BoardDto("no title", "123123", "asdf");
        assertTrue(boardDao.insert(boardDto)==1);

        int bno = boardDao.selectAll().get(0).getBno();
        boardDto.setBno(bno);

        BoardDto boardDto2 = boardDao.select(bno);
        assertTrue(boardDto.equals(boardDto2));
    }

    @Test
    public void selectAllTest() throws Exception {
        boardDao.deleteAll();
        assertTrue(boardDao.count()==0);

        List<BoardDto> list = boardDao.selectAll();
        assertTrue(list.size()==0);

        BoardDto boardDto = new BoardDto("no title", "no content", "asdf");
        assertTrue(boardDao.insert(boardDto)==1);

        list = boardDao.selectAll();
        assertTrue(list.size()==1);

        assertTrue(boardDao.insert(boardDto)==1);
        list = boardDao.selectAll();
        assertTrue(list.size()==2);
    }

    @Test
    public void countTest() throws Exception {
        boardDao.deleteAll();
        assertTrue(boardDao.count()==0);

        BoardDto boardDto = new BoardDto("title", "content", "asdf");
        assertTrue(boardDao.insert(boardDto)==1);
        assertTrue(boardDao.count()==1);

        assertTrue(boardDao.insert(boardDto)==1);
        assertTrue(boardDao.count()==2);

    }
    
    @Test
    public void updateTest() throws Exception {
        boardDao.deleteAll();
        assertTrue(boardDao.count()==0);

        BoardDto boardDto = new BoardDto("title", "content", "asdf");
        assertTrue(boardDao.insert(boardDto)==1);

        int bno = boardDao.selectAll().get(0).getBno();
        boardDto.setBno(bno);
        boardDto.setTitle("title2");
        boardDto.setContent("content2");
        assertTrue(boardDao.update(boardDto)==1);

        BoardDto boardDto2 = boardDao.select(bno);
        assertTrue(boardDto.equals(boardDto2));
    }

    @Test
    public void increaseViewCntTest() throws Exception {
        boardDao.deleteAll();
        assertTrue(boardDao.count()==0);

        BoardDto boardDto = new BoardDto("title", "content", "asdf");
        assertTrue(boardDao.insert(boardDto)==1);

        int bno = boardDao.selectAll().get(0).getBno();
        assertTrue(boardDao.increaseViewCnt(bno)==1);
        assertTrue(boardDao.select(bno).getView_cnt()==1);

        assertTrue(boardDao.increaseViewCnt(bno)==1);
        assertTrue(boardDao.select(bno).getView_cnt()==2);

    }

    @Test
    public void deleteTest() throws Exception {
        boardDao.deleteAll();
        assertTrue(boardDao.count()==0);

        BoardDto boardDto = new BoardDto("title", "content", "asdf");
        assertTrue(boardDao.insert(boardDto)==1);

        String writer = boardDto.getWriter();
        int bno = boardDao.selectAll().get(0).getBno();
        assertTrue(boardDao.delete(bno, writer)==1);
        assertTrue(boardDao.count()==0);

        assertTrue(boardDao.insert(boardDto)==1);
        bno = boardDao.selectAll().get(0).getBno();
        assertTrue(boardDao.delete(bno, writer+"2")==0);
        assertTrue(boardDao.count()==1);

        assertTrue(boardDao.delete(bno+1, writer)==0);
        assertTrue(boardDao.count()==1);

    }
    @Test
    public void deleteAllTest() throws Exception {
        boardDao.deleteAll();
        assertTrue(boardDao.count()==0);

        BoardDto boardDto = new BoardDto("no title", "no content", "asdf");
        assertTrue(boardDao.insert(boardDto)==1);
        assertTrue(boardDao.deleteAll()==1);
        assertTrue(boardDao.count()==0);

        boardDto = new BoardDto("no title", "no content", "asdf");
        assertTrue(boardDao.insert(boardDto)==1);
        assertTrue(boardDao.insert(boardDto)==1);
        assertTrue(boardDao.deleteAll()==2);
        assertTrue(boardDao.count()==0);
    }
}