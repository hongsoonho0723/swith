package com.sportsmania.swith.controller;

import com.sportsmania.swith.dto.MatchBoardDTO;
import com.sportsmania.swith.dto.PageRequestDTO;
import com.sportsmania.swith.service.MatchBoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class MatchBoardController {

    @Autowired
    MatchBoardService matchBoardService;

    /*@RequestMapping("/")
    public @ResponseBody
    String root() throws Exception {
        return "test1212";
    }*/

    @GetMapping("/match")
    public String list(@Valid PageRequestDTO pageRequestDTO, BindingResult bindingResult, Model model){

        log.info(pageRequestDTO);
        if(bindingResult.hasErrors()){
            pageRequestDTO=PageRequestDTO.builder().build();
        }

        model.addAttribute("responseDTO",matchBoardService.getList(pageRequestDTO));
        log.info(pageRequestDTO);
        return "match/blog-grid";

    }
    @GetMapping("/match/register")
    public String registerGET(){
        log.info("get register...");
        return "match/matching-create";
    }

    @PostMapping("/match/register")
    public String registerPOST(@Valid MatchBoardDTO matchBoardDTO, BindingResult bindingResult,
                               RedirectAttributes redirectAttributes) {
        log.info("post register...");
        String date = matchBoardDTO.getStartdate();
        String date2 = matchBoardDTO.getEnddate();
        String d = date.replaceAll("T"," ");
        String d2 = date2.replaceAll("T"," ");
        matchBoardDTO.setStartdate(d);
        matchBoardDTO.setEnddate(d2);
        log.info(matchBoardDTO);
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
        log.info(matchBoardDTO);
        matchBoardService.register(matchBoardDTO);
        return "redirect:/match";

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
