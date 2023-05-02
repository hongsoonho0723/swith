package com.sportsmania.swith.controller;

import com.sportsmania.swith.dto.AjaxDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@Log4j2
public class AjaxController {
    @GetMapping("/ex01")
    public String ex01(){
        log.info("AjaxController.ex01");
        return "/main/main";  //main.html에 작성된 내용이 출력됨
    }

    @PostMapping("/ex02")
    public @ResponseBody String ex02(){
        log.info("AjaxController.ex02");
        return "/main/main";  //@ResponseBody가 붙으면 리턴값 스트링형식으로 그대로 출력됨
    }

    @GetMapping("/ex03")
    public @ResponseBody String ex03(@RequestParam("param1") String param1,
                                     @RequestParam("param2") String param2){
        log.info("param1= " + param1);
        log.info("param2= " + param2);
        return "ex03메서드 호출 완료";
    }

    @PostMapping("/ex04")
    public @ResponseBody String ex04(@RequestParam("param1") String param1,
                                     @RequestParam("param2") String param2){
        log.info("param1= " + param1);
        log.info("param2= " + param2);
        return "ex04메서드 호출 완료";
    }

    @GetMapping("/ex05")
    public @ResponseBody AjaxDTO ex05(@ModelAttribute AjaxDTO ajaxDTO){
        log.info("ajaxDTO= " + ajaxDTO);
        return ajaxDTO;
    }

    @PostMapping("/ex06")
    public @ResponseBody AjaxDTO ex06(@ModelAttribute AjaxDTO ajaxDTO){
        log.info("ajaxDTO= " + ajaxDTO);
        return ajaxDTO;
    }

    @PostMapping("/ex07") // json방식으로 주고 받기 <- @RequestBody 사용
    public @ResponseBody
    AjaxDTO ex07(@RequestBody AjaxDTO ajaxDTO) {
        log.info("ajaxDTO=" + ajaxDTO);
        return ajaxDTO;
    }

    //DB안쓰므로 list만들어주는 메서드 하나 생성
    private List<AjaxDTO> DTOList(){
        List<AjaxDTO> dtoList = new ArrayList<>();
        dtoList.add(new AjaxDTO("data1", "data1"));
        dtoList.add(new AjaxDTO("data2", "data2"));
        return dtoList;
    }

    @PostMapping("/ex08") // json방식으로 주고 받기 <- @RequestBody 사용
    public @ResponseBody List<AjaxDTO> ex08(@RequestBody AjaxDTO ajaxDTO) {
        log.info("ajaxDTO=" + ajaxDTO);
        List<AjaxDTO> dtoList = DTOList();
        dtoList.add(ajaxDTO);
        return dtoList;
    }
    @PostMapping("/ex09")
    public ResponseEntity ex09(@RequestBody AjaxDTO ajaxDTO) {
        log.info("ajaxDTO=" + ajaxDTO);
        return new ResponseEntity<>(ajaxDTO, HttpStatus.OK);
    }
    @PostMapping("/ex10")
    public ResponseEntity ex10(@RequestBody AjaxDTO ajaxDTO) {
        log.info("ajaxDTO=" + ajaxDTO);
        List<AjaxDTO> dtoList = DTOList();
        dtoList.add(ajaxDTO);
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

}
