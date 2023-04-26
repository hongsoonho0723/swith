package com.sportsmania.swith.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/info")
public class MyController {

    @GetMapping("/main")
    public String main(){
        return "info/main";
    }

    @GetMapping("/signin")
    public String sign(){
        return "info/signin";
    }

    @GetMapping("/signup")
    public String signup(){
        return "info/signinup";
    }


}
