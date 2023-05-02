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
import java.util.List;

@Controller
@Log4j2
@RequestMapping("/")
@RequiredArgsConstructor
public class BoardController {
    @Autowired
    private final BoardService boardService;


    @GetMapping("/match")
    public String list(@Valid PageRequestDTO pageRequestDTO, BindingResult bindingResult, Model model){

        log.info(pageRequestDTO);
        if(bindingResult.hasErrors()){
            pageRequestDTO=PageRequestDTO.builder().build();
        }

        model.addAttribute("responseDTO",boardService.getList(pageRequestDTO));
        log.info(pageRequestDTO);
        return "match/blog-grid";

    }
    @GetMapping("/match/register")
    public String registerGET(){
        log.info("get register...");
        return "match/matching-create";
    }

    @PostMapping("/match/register")
    public String registerPOST(@Valid BoardDTO boardDTO, BindingResult bindingResult,
                               RedirectAttributes redirectAttributes) {
        log.info("post register...");
        log.info(boardDTO);
        String date = boardDTO.getStartdate();
        String date2 = boardDTO.getEnddate();

        log.info(date);
        log.info(date2);
        String d = date.replaceAll("T"," ");
        String d2 = date2.replaceAll("T"," ");
        boardDTO.setStartdate(d);
        boardDTO.setEnddate(d2);
        log.info(d);
        log.info(d2);
        log.info(boardDTO);
        if(bindingResult.hasErrors()) {
            log.info("register has errors...");
            List<ObjectError> errList = bindingResult.getAllErrors();
            for(ObjectError err:errList){
                String errMsg = ""+err.getDefaultMessage()+"\r\n toString() :"+err.toString();
                log.info(errMsg);
                System.out.println(" ");
            }
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/match/register";

        }
        log.info(boardDTO);
        boardService.register(boardDTO);
        return "redirect:/match";

    }

}
