package com.sportsmania.swith.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AjaxViewController {
    @GetMapping("/ajax-ex-01")
    public String ajaxEX01(){
        return "/ajax/ajax-ex-01";
    }

    @GetMapping("/ajax-ex-02")
    public String ajaxEX02(){
        return "/ajax/ajax-ex-02";
    }

    @GetMapping("/ajax-ex-03")
    public String ajaxEX03(){
        return "/ajax/ajax-ex-03";
    }

    @GetMapping("/ajax-ex-04")
    public String ajaxEX04(){
        return "/ajax/ajax-ex-04";
    }
    @GetMapping("/ajax-ex-05")
    public String ajaxEX05(){
        return "/ajax/ajax-ex-05";
    }
    @GetMapping("/ajax-ex-06")
    public String ajaxEX06(){
        return "/ajax/ajax-ex-06";
    }
    @GetMapping("/ajax-ex-07")
    public String ajaxEX07(){
        return "/ajax/ajax-ex-07";
    }
    @GetMapping("/ajax-ex-08")
    public String ajaxEX08(){
        return "/ajax/ajax-ex-08";
    }
    @GetMapping("/ajax-ex-09")
    public String ajaxEX09(){
        return "/ajax/ajax-ex-09";
    }
    @GetMapping("/ajax-ex-10")
    public String ajaxEX10(){
        return "/ajax/ajax-ex-10";
    }
}
