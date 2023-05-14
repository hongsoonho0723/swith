package com.sportsmania.swith.controller;

import com.sportsmania.swith.dto.SupportTeamDTO;
import com.sportsmania.swith.dto.UserDTO;
import com.sportsmania.swith.service.SupportTeamService;
import com.sportsmania.swith.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;


@RestController
@Log4j2
@RequiredArgsConstructor
public class ChatController {

    private final SupportTeamService supportTeamService;
    private final UserService userService;
    /*@GetMapping("/chatrooms")
    public ResponseEntity<Void> goChatroom(@RequestParam("chatRoomTitle") String chatRoomTitle,
                                           @RequestParam("nickname") String nickname,
                                           HttpServletResponse response) {
        log.info("chatRoomTitle:"+chatRoomTitle + ", nickname:"+nickname);
        String redirectUrl = "http://localhost:3000?chatRoomTitle=" + chatRoomTitle + "&nickname=" + nickname;
        response.setHeader("Location",redirectUrl);
        return new ResponseEntity<>(HttpStatus.FOUND);
    }*/



    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/teams/userInfo")
    public UserDTO getUser(Authentication authentication){
         String userId = authentication.getName();
        UserDTO userDTO = userService.findByUsername(userId);
        log.info("userId cors");
        return userDTO;
    }
}
