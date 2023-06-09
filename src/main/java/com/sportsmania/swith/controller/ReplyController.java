package com.sportsmania.swith.controller;

import com.sportsmania.swith.dto.ReplyDTO;
import com.sportsmania.swith.service.ReplyService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public ResponseEntity<ReplyDTO> register(@Valid @RequestBody ReplyDTO replyDTO, Authentication authentication) {
        log.info("댓글 등록:"  + replyDTO);

        String user_id = authentication.getName();

        replyDTO.setReply_writer(user_id);

        replyService.register(replyDTO);

        return new ResponseEntity<>(replyDTO, HttpStatus.OK);

    }

    @GetMapping("/{story_no}")
    public ResponseEntity<String> getList(@PathVariable("story_no") Long story_no){

        List<ReplyDTO> replyDTOList = replyService.getList(story_no);

        log.info("댓글 조회 요청 성공: " + replyDTOList);

        return new ResponseEntity(replyDTOList , HttpStatus.OK);
    }

    @GetMapping( "{story_no}/total")
    public ResponseEntity<Integer> getReplyCount(@PathVariable("story_no") Long story_no) {
        int replyCount = replyService.storyReplyCount(story_no);
        return new ResponseEntity<>(replyCount, HttpStatus.OK);
    }

    @DeleteMapping("/{reply_no}")
    public ResponseEntity<Void> remove(@PathVariable("reply_no") Long reply_no){
        log.info("댓글 삭제:" + reply_no);
        replyService.remove(reply_no);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{reply_no}")
    public ResponseEntity<ReplyDTO> modifyReply(@RequestBody ReplyDTO replyDTO) {
        log.info("컨트롤러 요청 성공");
        log.info(replyDTO.getStory_no());
        log.info(replyDTO.getContent());
        /*ReplyDTO originalReplyDTO = replyService.getReplyOne(replyDTO.getReply_no()); // 댓글 번호로 기존 댓글 정보 가져오기
        originalReplyDTO.setContent(replyDTO.getContent()); // 수정된 내용으로 댓글 내용 설정
        replyService.modify(originalReplyDTO); // DB에 수정된 댓글 정보 업데이트

        log.info("댓글 수정:" + originalReplyDTO);*/
        replyService.modify(replyDTO);

        return new ResponseEntity<>(HttpStatus.OK); // 수정된 댓글 정보 반환
    }
    @RestController
    public class UserController {

        @GetMapping("/api/getLoggedInUserId")
        public ResponseEntity<String> getLoggedInUserId() {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String loggedInUserId = authentication.getName(); // Get the username or user ID

            return new ResponseEntity<>(loggedInUserId, HttpStatus.OK);
        }
        }


}
