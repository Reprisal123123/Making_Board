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

    // ��� �ۼ�
    @PostMapping("/comments") // /ch6/comments?bno=1338 POST
    public ResponseEntity<String> write(@RequestBody CommentDto dto, Integer bno, HttpSession session) throws Exception {
        String commenter = (String)session.getAttribute("id");

        dto.setCommenter(commenter);
        dto.setBno(bno);

        Integer pcno = dto.getPcno(); // �ۼ��� ����� �θ��� ��ȣ�� ������

        if(pcno!=null) { // �θ����� �����ϴ� ���
            try {
                String prepno = service.read(pcno).getRepno(); // �θ��ۿ� �ִ� ���ù�ȣ�� ������

                String pcnoStr = String.valueOf(dto.getPcno()); // �θ��۹�ȣ�� ���ڿ��� ��ȯ
                for (int i=0; pcnoStr.length() < 5 ; i++) { // ���ڿ��� �ڸ����� 5�ڸ��� �ϰ� �� �ڸ��� 0���� ä��
                    pcnoStr = "0" + pcnoStr;
                }

                dto.setRepno(prepno + pcnoStr); // �θ����� ���ù�ȣ�� �θ��۹�ȣ�� ���Ͽ� ���ο� ���ù�ȣ�� ������

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

    // ������ �Խù��� ��� ����� �������� �޼ҵ�
    @GetMapping("/comments") // /ch6/comments?bno=1338 GET
//    @ResponseBody
    public ResponseEntity<List<CommentDto>> list(Integer bno) {
        List<CommentDto> list = null;
        try {
            list = service.getList(bno);
//            System.out.println("list = " + list);
            return new ResponseEntity<List<CommentDto>>(list, HttpStatus.OK); // 200��, ResponseEntity�� �����ڵ���� ���� �����°�
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<List<CommentDto>>(HttpStatus.BAD_REQUEST); // 400 ����
        }

    }

    // ��� ����
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
    @DeleteMapping("/comments/{cno}") // /ch6/comments/1?bno=1338 <-- ������ ��� ��ȣ
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
