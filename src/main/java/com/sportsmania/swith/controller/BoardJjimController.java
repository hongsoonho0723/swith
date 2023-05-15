package com.sportsmania.swith.controller;

import com.sportsmania.swith.dto.BoardJjimDTO;
import com.sportsmania.swith.service.BoardJjimService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@Log4j2
@RequestMapping("/match")
public class BoardJjimController {
    @Autowired
    private final BoardJjimService boardJjimService;

    public BoardJjimController(BoardJjimService boardJjimService) {
        this.boardJjimService = boardJjimService;
    }


    /*@GetMapping("/wish/{board_no}")
    public ResponseEntity<Boolean> isLiked(@PathVariable("board_no") Integer board_no, Authentication authentication ){
        log.info("booleancheck");
        String userId = authentication.getName();
        boolean isWish = boardJjimService.isWishByUser(board_no,userId);
        return new ResponseEntity<>(isWish, HttpStatus.OK);
    }*/

    @GetMapping("/wish/{board_no}")
    public ResponseEntity<Boolean> addLike(@PathVariable("board_no")int board_no, @RequestBody BoardJjimDTO boardJjimDTO, Authentication authentication){
            String userId = (authentication != null) ? authentication.getName() : null;
            boardJjimDTO.setUserid(userId);
            log.info("wish/board_no get..");
            log.info(boardJjimDTO);
        boolean isWish = false; // 기본값 설정

        if (userId != null) {
            isWish = boardJjimService.isWishByUser(board_no,userId);
        }

        return new ResponseEntity<>(isWish, HttpStatus.OK);
    }

    @PostMapping("/wish")
    public ResponseEntity<Boolean> addJjim(@io.swagger.v3.oas.annotations.parameters.RequestBody BoardJjimDTO boardJjimDTO, Authentication authentication) {
        String userId = authentication.getName();
        log.info(boardJjimDTO.getUserid());
        boardJjimDTO.setUserid(userId);
        log.info(boardJjimDTO);
        boolean isWish = boardJjimService.isWishByUser(boardJjimDTO.getBoard_no(), boardJjimDTO.getUserid());
        if (isWish) {
            boardJjimService.remove(boardJjimDTO);
        } else {
            boardJjimService.register(boardJjimDTO);
        }
        // 좋아요 취소 여부를 클라이언트에 전달
        return new ResponseEntity<>(isWish, HttpStatus.CREATED);
    }

    /*@DeleteMapping("/wish")
    public ResponseEntity removeLike(@RequestBody BoardJjimDTO boardJjimDTO){
        boardJjimService.remove(boardJjimDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }*/

}
