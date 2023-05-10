package com.sportsmania.swith.controller;

import com.sportsmania.swith.domain.UserVO;
import com.sportsmania.swith.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@Log4j2
public class CheckController {

    @Autowired
    private UserService userService;

    @GetMapping("/check-email")
    public Map<String, Boolean> checkEmailDuplicate(@RequestParam("email") String email) {
        log.info(email);
        boolean duplicate = userService.existsByEmail(email);
        Map<String, Boolean> response = new HashMap<>();
        response.put("duplicate", duplicate);
        log.info(response);
        return response;
    }

    @GetMapping("/check-nickname")
    public Map<String, Boolean> checknicknameDuplicate(@RequestParam("nickname") String nickname) {
        log.info(nickname);
        boolean duplicate = userService.existsBynickname(nickname);
        Map<String, Boolean> response2 = new HashMap<>();
        response2.put("duplicate", duplicate);
        log.info(response2);
        return response2;
    }


    @GetMapping("/checkDuplicateId")
    public Map<String, Boolean> checkDuplicateId(@RequestParam("userId") String userId) {
        log.info(userId);
        boolean duplicate = userService.checkDuplicateId(userId);
        Map<String, Boolean> response = new HashMap<>();
        response.put("duplicate", duplicate);

        log.info(response);
        return response;
    }

    @PostMapping("/check_user")
    public Map<String, String> checkUser(@RequestParam("name") String name,@RequestParam("email") String email){
        log.info(name);
        Map<String, String> response = new HashMap<>();
        UserVO userVO = userService.userCheck(name,email);
        if(userVO != null){
            response.put("userId",userVO.getUserId());
        }
        log.info(response);
        return response;
    }
}
