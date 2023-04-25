package com.sportsmania.swith.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping("/main")
    public String main(){
        return "/main/main";
    }

    @GetMapping("/chat")
    public String chat(){
        return "/chat/chat";
    }
}
