package com.sportsmania.swith.controller;

import com.sportsmania.swith.dto.MatchBoardDTO;
import com.sportsmania.swith.dto.UserDTO;
import com.sportsmania.swith.service.MatchBoardService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Log4j2
@Controller
public class MatchBoardController {

    @Autowired
    MatchBoardService matchBoardService;



        /*@GetMapping("/match/view")
        public String read(@RequestParam("board_no") String board_no, Authentication authentication,Model model) {
            int board_no1 = Integer.parseInt(board_no);
            String userId = authentication.getName();
            MatchBoardDTO matchBoardDTO = matchBoardService.getOne(board_no1);
            UserDTO userDTO = matchBoardService.getOne1(userId);
            model.addAttribute("user", userDTO);
            log.info("userDTO1111:" + userDTO);
            log.info("matchBoardDTO111:" + matchBoardDTO);
            model.addAttribute("dto", matchBoardDTO);
            return "match/matching-view";
}*/


   /* @GetMapping("/match/view")
    public String read(Integer board_no,  String userId, Model model) {
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
        return "/match/matching-view";

    }*/





    @GetMapping("/match/modify")
    public String read1(Integer board_no, String userId, Model model) {
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
        return "/match/matching-modify";
    }








    @PostMapping("/match/matching-modify")
    public String modify(UserDTO userDTO, MatchBoardDTO matchBoardDTO ){

        log.info(matchBoardDTO);
        matchBoardService.modify(matchBoardDTO);

        return "redirect:/match/view?board_no=" + matchBoardDTO.getBoard_no() + "&userId=" + userDTO.getUserId();
    }



    @PostMapping("/remove")
    public String remove(int board_no, RedirectAttributes redirectAttributes){
        log.info("-------------remove--------------");
        log.info("board_no="+board_no);
        matchBoardService.remove(board_no);
        return "redirect:/match";
    }



}
