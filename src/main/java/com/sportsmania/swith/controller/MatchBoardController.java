package com.sportsmania.swith.controller;

import com.sportsmania.swith.dto.MatchBoardDTO;
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
      public void read(int board_no, Model model){
          MatchBoardDTO matchBoardDTO=matchBoardService.getOne(board_no);
          log.info(matchBoardDTO);
          model.addAttribute("dto",matchBoardDTO);

      }
    @GetMapping("/match/matching-modify")
    public void read1(int board_no, Model model){
        MatchBoardDTO matchBoardDTO=matchBoardService.getOne(board_no);
        log.info(matchBoardDTO);
        log.info(board_no);
        model.addAttribute("dto",matchBoardDTO);

    }

    @PostMapping("/match/matching-modify")
    public String modify( MatchBoardDTO matchBoardDTO ){

        log.info(matchBoardDTO);
        matchBoardService.modify(matchBoardDTO);

        return "redirect:/match/blog-grid";
    }

    @PostMapping("/remove")
    public String remove(int board_no, RedirectAttributes redirectAttributes){
        log.info("-------------remove--------------");
        log.info("board_no="+board_no);
        matchBoardService.remove(board_no);
        return "redirect:match/blog-grid";
    }




}
