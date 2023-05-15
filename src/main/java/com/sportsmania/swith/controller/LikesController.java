package com.sportsmania.swith.controller;

import com.sportsmania.swith.dto.LikesDTO;
import com.sportsmania.swith.service.LikesService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@Log4j2
@RequestMapping("/stories/likes")
public class LikesController {

    @Autowired
    private  final LikesService likesService;


    public LikesController(LikesService likesService) {
        this.likesService = likesService;
    }

    /*@GetMapping("/{story_no}")
    public ResponseEntity<Boolean> isLiked(@PathVariable("story_no") Long story_no,Authentication authentication){
        String userId = authentication.getName();
        boolean isLiked = likesService.isLikedByUser(story_no, userId);
        return new ResponseEntity<>(isLiked, HttpStatus.OK);
    }
*/
    @GetMapping("/{story_no}")
    public ResponseEntity<Boolean> isLiked(@PathVariable("story_no") Long story_no, Authentication authentication) {
        String userId = (authentication != null) ? authentication.getName() : null;
        boolean isLiked = false; // 기본값 설정

        if (userId != null) {
            isLiked = likesService.isLikedByUser(story_no, userId);
        }

        return new ResponseEntity<>(isLiked, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Boolean> addLike(@RequestBody LikesDTO likesDTO, Authentication authentication) {
        String userId = authentication.getName();
        likesDTO.setUserId(userId);
        boolean isLiked = likesService.isLikedByUser(likesDTO.getStory_no(), likesDTO.getUserId());
        if (isLiked) {
            likesService.removeLike(likesDTO);
        } else {
            likesService.addLike(likesDTO);
        }
        // 좋아요 취소 여부를 클라이언트에 전달
        return new ResponseEntity<>(isLiked, HttpStatus.CREATED);
    }


}
