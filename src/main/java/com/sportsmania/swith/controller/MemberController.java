package com.sportsmania.swith.controller;

import com.sportsmania.swith.dto.UserDTO;
import com.sportsmania.swith.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/info")
@Log4j2
public class MemberController {

    @Value("${com.sportsmania.upload.path}")
    private String uploadPath;

    @Autowired
    private UserService userService;


    @GetMapping("/mypage")
    public void myPage(){

    }

    @GetMapping("/my")
    public String modify(HttpSession httpSession){
        return "info/mypageModify";
    }

    @PostMapping("/my")
    public String modify1(HttpSession httpSession, @RequestParam("email") String email,@RequestParam("preference") String preference,
                            @RequestParam("introduction") String introduction,@RequestParam("nickname") String nickname, @RequestParam("file") MultipartFile file){

        String filename = file.getOriginalFilename();


        UserDTO dto = (UserDTO) httpSession.getAttribute("user");
        log.info(dto);
        log.info(filename);
        dto.setEmail(email);
        dto.setNickname(nickname);
        dto.setPreference(preference);
        dto.setIntroduction(introduction);
        userService.modify(dto);
        return "redirect:/info/mypage";
    }

    @GetMapping("/history")
    public void history(HttpSession httpSession){
        UserDTO dto = (UserDTO) httpSession.getAttribute("user");
        log.info("============");
        log.info(dto);
    }

}
