package com.sportsmania.swith.controller;

import com.sportsmania.swith.dto.BoardDTO;
import com.sportsmania.swith.dto.PageRequestDTO;
import com.sportsmania.swith.dto.PageResponseDTO;
import com.sportsmania.swith.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@Controller
@Log4j2
@RequestMapping("/match")
@RequiredArgsConstructor
public class BoardController {
    @Autowired
    private final BoardService boardService;


    @GetMapping("")
    public String list(@Valid PageRequestDTO pageRequestDTO, BindingResult bindingResult, Model model){

        log.info(pageRequestDTO);
        if(bindingResult.hasErrors()){
            pageRequestDTO=PageRequestDTO.builder().build();
        }
//        #{roomtitle} = board_no+b_category+nickname
//        String getB_cate = pageRequestDTO.getB_category();
//        int getBoard_no = (int) boardService.getList(pageRequestDTO).getDtoList().get(0).getBoard_no();
//        String getUserNickname =
        model.addAttribute("responseDTO",boardService.getList(pageRequestDTO));
        log.info(boardService.getList(pageRequestDTO).getDtoList().get(0).getAdditionalNum());

        log.info(pageRequestDTO);
        return "match/list";

    }
    @GetMapping("/posts")
    public String registerGET(){
        log.info("get register...");
        return "match/matching-create";
    }



}
