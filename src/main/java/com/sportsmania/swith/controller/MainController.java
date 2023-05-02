package com.sportsmania.swith.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MainController {
    @GetMapping("/main")
    public String main(){
        return "/main/main";
    }

    /*@GetMapping("/chat")
    public String chat(){
        return "/chat/chat";
    }*/

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/chat")
    public void chatting(Model model) {
        model.addAttribute("re", "re");
    }


}


