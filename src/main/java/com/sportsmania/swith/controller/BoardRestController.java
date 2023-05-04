package com.sportsmania.swith.controller;

import com.sportsmania.swith.dto.BoardDTO;
import com.sportsmania.swith.dto.BoardJjimDTO;
import com.sportsmania.swith.service.BoardJjimService;
import com.sportsmania.swith.service.BoardService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Log4j2
@RestController
public class BoardRestController {
    @Autowired
    private final BoardService boardService;
    private final BoardJjimService boardJjimService;


    public BoardRestController(BoardService boardService, BoardJjimService boardJjimService) {  this.boardService = boardService;
        this.boardJjimService = boardJjimService;
    }

    @PostMapping("match/posts")
    public ResponseEntity<BoardDTO> registerPOST(@Valid @RequestBody BoardDTO boardDTO, BindingResult bindingResult,
                                                 RedirectAttributes redirectAttributes) {
        log.info("post register...");
        log.info(boardDTO);
        String date = boardDTO.getStartdate();
        String date2 = boardDTO.getEnddate();
        String d = date.replaceAll("T"," ");
        String d2 = date2.replaceAll("T"," ");
        boardDTO.setStartdate(d);
        boardDTO.setEnddate(d2);
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
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        }
        boardService.register(boardDTO);
        return  new ResponseEntity<>(boardDTO, HttpStatus.OK);

    }

    //찜하기
    @PostMapping("/match/wish")
    public ResponseEntity wish(@RequestBody BoardJjimDTO boardJjimDTO){
        boardJjimService.register(boardJjimDTO);
        log.info(boardJjimDTO);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
