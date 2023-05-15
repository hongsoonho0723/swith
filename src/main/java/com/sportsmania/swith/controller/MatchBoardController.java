package com.sportsmania.swith.controller;

import com.sportsmania.swith.dto.MatchBoardDTO;
import com.sportsmania.swith.dto.UserDTO;
import com.sportsmania.swith.service.BoardJjimService;
import com.sportsmania.swith.service.MatchBoardService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@Controller
public class MatchBoardController {

    @Autowired
    MatchBoardService matchBoardService;
    @Autowired
    BoardJjimService boardJjimService;



    @GetMapping("/match/view")
    public String read(Integer board_no, Model model, HttpSession session) {
        if (board_no == null ) {
            log.info("board_no:"+board_no);
            log.info("null발생!!");
            // 처리할 로직 추가
        } else {
            // board_no로 MatchBoardDTO 조회
            MatchBoardDTO matchBoardDTO = matchBoardService.getOne(board_no);
            log.info(matchBoardDTO);
            // 세션에 저장된 게시글 번호 리스트를 가져옵니다.
            List<Integer> viewedStoryList = (List<Integer>) session.getAttribute("viewedStoryList");
            if (viewedStoryList == null) {
                viewedStoryList = new ArrayList<>();
            }

            // 현재 게시글 번호가 세션에 저장된 리스트에 없으면 조회수를 증가합니다.
            if (!viewedStoryList.contains(board_no)) {
                matchBoardService.increaseViewCount(board_no);
                viewedStoryList.add(board_no);
                session.setAttribute("viewedStoryList", viewedStoryList);
            }
            model.addAttribute("dto", matchBoardDTO);
            log.info(matchBoardDTO.getIntroduction());
            // userId로 UserDTO 조회

        }
        return "match/matching-view";

    }



    @GetMapping("/match/modify")
    public String read1(Integer board_no, Model model) {
        if (board_no == null ) {
            log.info("board_no:"+board_no);
            log.info("null발생!!");
            // 처리할 로직 추가
        } else {
            // board_no로 MatchBoardDTO 조회
            MatchBoardDTO matchBoardDTO = matchBoardService.getOne(board_no);
            log.info(matchBoardDTO);
            model.addAttribute("dto", matchBoardDTO);


        }
        return "match/matching-modify";
    }






    @PostMapping("/match/matching-view")
    public String modify1(MatchBoardDTO matchBoardDTO){
        log.info(matchBoardDTO);
        matchBoardService.modify1(matchBoardDTO);
        log.info("모집완료"+matchBoardDTO);
        return "redirect:/match/view?board_no=" + matchBoardDTO.getBoard_no();
    }


    @PostMapping("/match/matching-modify")
    public String modify(UserDTO userDTO, MatchBoardDTO matchBoardDTO ){

        log.info(matchBoardDTO);
        matchBoardService.modify(matchBoardDTO);

        return "redirect:/match/view?board_no=" + matchBoardDTO.getBoard_no();
    }



    @PostMapping("/remove")
    public String remove(int board_no, RedirectAttributes redirectAttributes){
        log.info("-------------remove--------------");
        log.info("board_no="+board_no);

        boardJjimService.deleteAllByBoard_no(board_no);
        matchBoardService.remove(board_no);
        return "redirect:/match";
    }



}
