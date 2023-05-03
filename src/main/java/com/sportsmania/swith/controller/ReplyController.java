package com.sportsmania.swith.controller;

import com.sportsmania.swith.dto.ReplyDTO;
import com.sportsmania.swith.service.ReplyService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/replies")
@RestController
@Log4j2
public class ReplyController {

    @Autowired
    private ReplyService replyService;

    @PostMapping(value = "/")
    public ResponseEntity<ReplyDTO> register(@Valid @RequestBody ReplyDTO replyDTO) {
        log.info("댓글 등록:"  + replyDTO);

        replyService.register(replyDTO);

        return new ResponseEntity<>(replyDTO, HttpStatus.OK);

    }

    @GetMapping("/{story_no}")
    public ResponseEntity<String> getList(@PathVariable("story_no") Long story_no){

        List<ReplyDTO> replyDTOList = replyService.getList(story_no);

        return new ResponseEntity(replyDTOList , HttpStatus.OK);
    }

    @DeleteMapping("/{reply_no}")
    public ResponseEntity<Void> remove(@PathVariable("reply_no") Long reply_no){
        log.info("댓글 삭제:" + reply_no);
        replyService.remove(reply_no);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{reply_no}")
    public ResponseEntity<ReplyDTO> modifyReply(@PathVariable("reply_no") Long reply_no, @RequestBody ReplyDTO replyDTO) {
        replyDTO.setReply_no(reply_no); // reply_no 설정
        replyService.modify(replyDTO);

        log.info("댓글 수정:" + replyDTO);

        return new ResponseEntity<>(replyDTO, HttpStatus.OK);
    }

}
