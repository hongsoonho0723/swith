package com.sportsmania.swith.controller;

import com.sportsmania.swith.domain.ReplyVO;
import com.sportsmania.swith.dto.ReplyDTO;
import com.sportsmania.swith.service.ReplyService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequestMapping("/replies")
@RestController
@Log4j2
public class ReplyController {

    @Autowired
    private ReplyService replyService;

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReplyDTO> register(@Valid @RequestBody ReplyDTO replyDTO) {
        log.info("댓글 등록:"  + replyDTO);

        replyService.register(replyDTO);

        return new ResponseEntity<>(replyDTO, HttpStatus.OK);

    }


}
