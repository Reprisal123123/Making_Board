package com.fastcampus.ch6.controller;

import com.fastcampus.ch6.domain.CommentDto;
import com.fastcampus.ch6.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class CommentController {
    @Autowired
    CommentService service;

    // 댓글 작성
    @PostMapping("/comments") // /ch6/comments?bno=1338 POST
    public ResponseEntity<String> write(@RequestBody CommentDto dto, Integer bno, HttpSession session) throws Exception {
        String commenter = (String)session.getAttribute("id");

        dto.setCommenter(commenter);
        dto.setBno(bno);

        Integer pcno = dto.getPcno(); // 작성한 댓글의 부모댓글 번호를 가져옴

        if(pcno!=null) { // 부모댓글이 존재하는 경우
            try {
                String prepno = service.read(pcno).getRepno(); // 부모댓글에 있는 리플번호를 가져옴

                String pcnoStr = String.valueOf(dto.getPcno()); // 부모댓글번호를 문자열로 변환
                for (int i=0; pcnoStr.length() < 5 ; i++) { // 문자열의 자릿수를 5자리로 하고 빈 자리는 0으로 채움
                    pcnoStr = "0" + pcnoStr;
                }

                dto.setRepno(prepno + pcnoStr); // 부모댓글의 리플번호와 부모댓글번호를 더하여 새로운 리플번호를 저장함

            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity<>("READ_ERR", HttpStatus.BAD_REQUEST);
            }

        }

//        System.out.println("dto = " + dto);

        try {
            if(service.write(dto)!=1)
                throw new Exception("Write failed.");

            return new ResponseEntity<>("WRT_OK", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("WRT_ERR", HttpStatus.BAD_REQUEST);
        }
    }

    // 지정한 게시물의 모든 댓글을 가져오는 메소드
    @GetMapping("/comments") // /ch6/comments?bno=1338 GET
//    @ResponseBody
    public ResponseEntity<List<CommentDto>> list(Integer bno) {
        List<CommentDto> list = null;
        try {
            list = service.getList(bno);
//            System.out.println("list = " + list);
            return new ResponseEntity<List<CommentDto>>(list, HttpStatus.OK); // 200번, ResponseEntity는 상태코드까지 같이 보내는것
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<List<CommentDto>>(HttpStatus.BAD_REQUEST); // 400 에러
        }

    }

    // 댓글 수정
   @PatchMapping("/comments/{cno}") // /ch6/comments/70  PATCH
    public ResponseEntity<String> modify(@PathVariable Integer cno, @RequestBody CommentDto dto, HttpSession session) {
        String commenter = (String)session.getAttribute("id");
        dto.setCommenter(commenter);
        dto.setCno(cno);

        try {
            if(service.modify(dto)!=1)
                throw new Exception("Write failed.");

            return new ResponseEntity<>("MOD_OK", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("MOD_ERR", HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("/comments/{cno}") // /ch6/comments/1?bno=1338 <-- 삭제할 댓글 번호
    public ResponseEntity<String> remove(@PathVariable Integer cno, Integer bno, HttpSession session) {

        String commenter = (String)session.getAttribute("id");
        try {
            int rowCnt = service.remove(bno, cno, commenter);

            if(rowCnt!=1)
                throw new Exception("Delete Failed");

            return new ResponseEntity<>("DEL_OK", HttpStatus.OK);
        } catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("DEL_ERR", HttpStatus.BAD_REQUEST);
        }

    }


}
