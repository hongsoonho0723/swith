package com.sportsmania.swith.controller;

import com.sportsmania.swith.domain.UserVO;
import com.sportsmania.swith.dto.UserDTO;
import com.sportsmania.swith.service.EmailService;
import com.sportsmania.swith.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.HashMap;
import java.util.Map;

@RestController
@Log4j2
public class CheckController {

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

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

    @PostMapping("/find_pwd")
    public Map<String, String> findPwd(@RequestParam("name") String name,@RequestParam("email") String email,@RequestParam("userId") String userId) throws MessagingException {
        log.info(name);
        Map<String, String> response = new HashMap<>();
        UserVO userVO = userService.findPwd(name,email,userId);
        if(userVO != null){
            response.put("username",userVO.getName());
            emailService.sendVerificationEmail(email);
        }
        log.info(response);
        return response;
    }

    @PostMapping("/verify")
    public Map<String, Boolean> code(@RequestParam("code") String code,@RequestParam("email")String email){
        Cache cache = cacheManager.getCache("verificationCodes");
        String key = cache.get(email,String.class);
        Map<String, Boolean> response = new HashMap<>();
        if (code.equals(key)){
            response.put("duplicate", true);
        }

        log.info(code);
        return response;
    }

    @PostMapping("/modify_pwd")
    public Map<String, Boolean> modi_pwd(@RequestParam("pwd") String pwd,@RequestParam("userId")String userId){
        Map<String, Boolean> response = new HashMap<>();
        UserDTO dto= new UserDTO();
        dto.setPwd(bCryptPasswordEncoder.encode(pwd));
        dto.setUserId(userId);
        userService.modifyPwd(dto);
        response.put("check",true);

        return response;
    }
}
