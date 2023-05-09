package com.sportsmania.swith.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;


@RestController
@Log4j2
public class ChatController {
    @GetMapping("/chatrooms")
    public ResponseEntity goChatroom() {
        log.info("/chatrooms 컨트롤러 진입");
        return new ResponseEntity(HttpStatus.OK);
    }
}
