package com.sportsmania.swith.controller;

import com.sportsmania.swith.dto.MatchBoardDTO;
import com.sportsmania.swith.dto.UserDTO;
import com.sportsmania.swith.service.MatchBoardService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Log4j2
@Controller
public class MatchBoardController {

    @Autowired
    MatchBoardService matchBoardService;

    @RequestMapping("/")
    public @ResponseBody
    String root() throws Exception {
        return "test1212";
    }



    @GetMapping("/match/matching-view")
    public void read(Integer board_no, String userId, Model model) {
        if (board_no == null || userId == null) {
            log.info("board_no:"+board_no);
            log.info("userId:"+userId);
            log.info("null발생!!");
            // 처리할 로직 추가
        } else {
            // board_no로 MatchBoardDTO 조회
            MatchBoardDTO matchBoardDTO = matchBoardService.getOne(board_no);
            log.info(matchBoardDTO);
            model.addAttribute("dto", matchBoardDTO);

            // userId로 UserDTO 조회
            UserDTO userDTO = matchBoardService.getOne1(userId);
            log.info(userDTO);
            log.info(userId);
            model.addAttribute("user", userDTO);
        }
    }








    @GetMapping("/match/matching-modify")
    public void read1(Integer board_no, String userId, Model model) {
        if (board_no == null || userId == null) {
            log.info("board_no:"+board_no);
            log.info("userId:"+userId);
            log.info("null발생!!");
            // 처리할 로직 추가
        } else {
            // board_no로 MatchBoardDTO 조회
            MatchBoardDTO matchBoardDTO = matchBoardService.getOne(board_no);
            log.info(matchBoardDTO);
            model.addAttribute("dto", matchBoardDTO);

            // userId로 UserDTO 조회
            UserDTO userDTO = matchBoardService.getOne1(userId);
            log.info(userDTO);
            log.info(userId);
            model.addAttribute("user", userDTO);
        }
    }








    @PostMapping("/match/matching-modify")
    public String modify( MatchBoardDTO matchBoardDTO ){

        log.info(matchBoardDTO);
        matchBoardService.modify(matchBoardDTO);

        return "redirect:/";
    }

    @GetMapping("/match/blog-grid")
    public String test14() {
        log.info("test1 ok");
        return "match/blog-grid";}



    @PostMapping("/remove")
    public String remove(int board_no, RedirectAttributes redirectAttributes){
        log.info("-------------remove--------------");
        log.info("board_no="+board_no);
        matchBoardService.remove(board_no);
        return "redirect:match/blog-grid";
    }

    @GetMapping("/match/mapapi")
    public String test15(){
        log.info("map ok");
        return "match/mapapi";
    }


}
