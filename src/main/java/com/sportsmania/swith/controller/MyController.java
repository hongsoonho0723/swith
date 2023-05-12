package com.sportsmania.swith.controller;


import com.sportsmania.swith.dto.BoardDTO;
import com.sportsmania.swith.dto.UserDTO;
import com.sportsmania.swith.service.BoardService;
import com.sportsmania.swith.service.UserService;

import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;


@Controller
@RequestMapping(value = "/members")
@Log4j2
public class MyController {


    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private ModelMapper modelMapper;

    @Autowired
    private UserService userService;
    @Autowired
    private final BoardService boardService;

    public MyController(BoardService boardService) {
        this.boardService = boardService;
    }


    @GetMapping("/main")
    public String main(HttpSession session, BoardDTO boardDTO, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();
        model.addAttribute("boardDTO",boardService.mainList(boardDTO));

        if (username.equals("anonymousUser")) {
            return "members/main";
        } else {
            log.info(username);
            UserDTO dto = userService.findByUsername(username);
            log.info(dto);
            session.setAttribute("user", dto);
            return "members/main";
        }
    }

    // 회원가입할때 권한 설정해줌
    @PostMapping(value = "/signup")
    public String postMapping(@ModelAttribute UserDTO dto) {
        log.info(dto);
        dto.setAuth("ROLE_USER");
        dto.setPwd(bCryptPasswordEncoder.encode(dto.getPwd()));
        userService.join(dto);
        return "redirect:/members/main";
    }


    @GetMapping("/signin")
    public String sign() {
        return "members/signin";
    }


    @GetMapping("/signup")
    public String signup() {
        return "members/signup";
    }

    @GetMapping(value = "/login/info")
    public String find(){
        return "members/find";
    }

    @GetMapping("logout")
    public String logout(HttpSession session, RedirectAttributes redirectAttributes) {
        session.invalidate();
        redirectAttributes.addFlashAttribute("message", "로그아웃되었습니다."); // 리다이렉트 시 전달할 데이터 추가
        return "redirect:/members/main";
    }




}
