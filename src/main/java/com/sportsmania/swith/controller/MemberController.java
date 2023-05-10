package com.sportsmania.swith.controller;

import com.sportsmania.swith.dto.UserDTO;
import com.sportsmania.swith.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Controller
@RequestMapping("/info")
@Log4j2
public class MemberController {


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
    public String modify1(HttpSession httpSession, @RequestParam("file") MultipartFile file, @ModelAttribute UserDTO userDTO, Model model) throws IOException {

       // String uploadPath = "C:\\upload\\"; //프로젝트 외부 c드라이브에 upload폴더에 저장
        String UPLOAD_DIR = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\assets\\user_image\\";

        String originalFileName = file.getOriginalFilename();

        String filename = UUID.randomUUID() + originalFileName; //동일한 파일명을 가진 파일이 저장될 경우 에러가 발생할 수 있기 때문에 파일명을 변경해여 저장

        byte[] bytes = file.getBytes();
        Path path = Paths.get(UPLOAD_DIR + filename);
        Files.write(path, bytes);


        //userDTO.setImage_profile(path.toString());
        String savePath = "../assets/user_image/" + filename;
        log.info(userDTO);

        log.info("==============");

        UserDTO dto = (UserDTO) httpSession.getAttribute("user");

        log.info(dto);

        dto.setImage_profile(savePath);
        dto.setEmail(userDTO.getEmail());
        dto.setNickname(userDTO.getNickname());
        dto.setPreference(userDTO.getPreference());
        dto.setIntroduction(userDTO.getIntroduction());
        model.addAttribute("dto",dto);
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
