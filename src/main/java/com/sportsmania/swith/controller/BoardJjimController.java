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


    @GetMapping("/wish/{board_no}")
    public ResponseEntity<Boolean> isLiked(@PathVariable("board_no") Integer board_no, Authentication authentication ){
        String userId = authentication.getName();
        boolean isWish = boardJjimService.isWishByUser(board_no,userId);
        return new ResponseEntity<>(isWish, HttpStatus.OK);
    }

   /* @PostMapping("/")
    public ResponseEntity<Boolean> addLike(@RequestBody LikesDTO likesDTO, Authentication authentication){
            String userId = authentication.getName();
            likesDTO.setUserId(userId);
            log.info(likesDTO);
            boolean isLiked = likesService.isLikedByUser(likesDTO.getStory_no(), likesDTO.getUserId());
            log.info(isLiked);
            if(isLiked == true)likesService.removeLike(likesDTO);
            else likesService.addLike(likesDTO);
            return new ResponseEntity<>(isLiked,HttpStatus.CREATED);
    }*/

    @PostMapping("/wish")
    public ResponseEntity<Boolean> addJjim(@io.swagger.v3.oas.annotations.parameters.RequestBody BoardJjimDTO boardJjimDTO, Authentication authentication) {
        String userId = authentication.getName();
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

    @DeleteMapping("/wish")
    public ResponseEntity removeLike(@RequestBody BoardJjimDTO boardJjimDTO){
        boardJjimService.remove(boardJjimDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
