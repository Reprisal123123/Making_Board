package com.fastcampus.ch6.service;

import com.fastcampus.ch6.domain.BoardDto;
import com.fastcampus.ch6.domain.SearchCondition;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class BoardServiceTest {
    @Autowired
    BoardService boardService;
    
    @Test
    public void getSearchResultCnt() throws Exception {
        SearchCondition sc = new SearchCondition(1, 10, "1", "A");
        int resultCnt = boardService.getSearchResultCnt(sc);
        System.out.println("resultCnt = " + resultCnt);

        }

    @Test
    public void getSearchResultPage() {
    }
}