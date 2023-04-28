package com.sportsmania.swith.Controller;

import com.sportsmania.swith.DTO.UserDTO;
import com.sportsmania.swith.Service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@RequestMapping("/members")
@Log4j2
public class MyController {


    @Autowired
    UserService userService;


    @GetMapping("/main")
    public String main(){
        return "members/main";
    }

    @GetMapping("/signin")
    public String sign(){
        return "members/signin";
    }

    @PostMapping("/signin")
    public String login(@RequestParam("userId") String userId, @RequestParam("pwd") String pwd, HttpSession session){
        Optional<UserDTO> result = Optional.ofNullable(userService.login(userId, pwd));

        if(result.isEmpty()){
            return "members/signin";
        }else{
            UserDTO dto = result.get();
            log.info(result);
            log.info(dto);
            session.setAttribute("user",dto);

            return "redirect:/members/main";
        }
    }
    @GetMapping("/signup")
    public String signup(){
        return "members/signinup";
    }

    @GetMapping("logout")
    public String logout(HttpSession session, RedirectAttributes redirectAttributes){
        session.invalidate();
        redirectAttributes.addFlashAttribute("message", "로그아웃되었습니다."); // 리다이렉트 시 전달할 데이터 추가
        return "redirect:/members/main";
    }

}
