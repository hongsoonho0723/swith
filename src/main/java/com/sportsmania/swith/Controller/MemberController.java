package com.sportsmania.swith.Controller;

import com.sportsmania.swith.DTO.UserDto;
import com.sportsmania.swith.Service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

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
    public String modify1(HttpSession httpSession, @RequestParam("email") String email,@RequestParam("preference") String preference,
                            @RequestParam("introduction") String introduction,@RequestParam("nickname") String nickname){
        UserDto dto = (UserDto) httpSession.getAttribute("user");
        dto.setEmail(email);
        dto.setNickname(nickname);
        dto.setPreference(preference);
        dto.setIntroduction(introduction);
        userService.modify(dto);
        return "redirect:/info/mypage";
    }

}
