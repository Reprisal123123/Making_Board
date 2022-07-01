package com.fastcampus.ch6.service;

import com.fastcampus.ch6.dao.BoardDao;
import com.fastcampus.ch6.dao.CommentDao;
import com.fastcampus.ch6.domain.CommentDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

//    @Autowired
    CommentDao commentDao;
//    @Autowired
    BoardDao boardDao;

    @Autowired
    public CommentServiceImpl(CommentDao commentDao, BoardDao boardDao) {
        this.commentDao = commentDao;
        this.boardDao = boardDao;
    } // 인스턴스 주입보다 생성자 주입을 권장함(컴파일 단계에서 오류를 알 수 있으므로 실수가 적어짐)
    // 생성자가 하나일 때만 Autowired 사용 가능

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int write(CommentDto commentDto) throws Exception {
        boardDao.updateCommentCnt(commentDto.getBno(), 1);
        return commentDao.insert(commentDto);
    }

    @Override
    public CommentDto read(Integer cno) throws Exception {
        return commentDao.select(cno);
    }

    @Override
    public List<CommentDto> getList(Integer bno) throws Exception {
        return commentDao.selectAll(bno);
    }

    @Override
    public int getCount(Integer bno) throws Exception {
        return commentDao.count(bno);
    }

    @Override
    public int modify(CommentDto commentDto) throws Exception {
        return commentDao.update(commentDto);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int remove(Integer bno, Integer cno, String commenter) throws Exception {
        boardDao.updateCommentCnt(bno, -1);
        return commentDao.delete(cno, commenter);
    }

}
