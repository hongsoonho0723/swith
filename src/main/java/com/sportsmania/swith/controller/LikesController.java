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

    @GetMapping("/{story_no}")
    public ResponseEntity<Boolean> isLiked(@PathVariable("story_no") LikesDTO likesDTO){
        boolean isLiked = likesService.isLikedByUser(likesDTO.getStory_no(), likesDTO.getUserId());
        return new ResponseEntity<>(isLiked, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Boolean> addLike(@RequestBody LikesDTO likesDTO, Authentication authentication){
            String userId = authentication.getName();
            likesDTO.setUserId(userId);
            log.info(likesDTO);
            boolean isLiked = likesService.isLikedByUser(likesDTO.getStory_no(), likesDTO.getUserId());
            log.info(isLiked);
            if(isLiked == true)likesService.removeLike(likesDTO);
            else likesService.addLike(likesDTO);
            return new ResponseEntity<>(isLiked,HttpStatus.CREATED);
    }

    @DeleteMapping("/")
    public ResponseEntity removeLike(@RequestBody LikesDTO likesDTO){
        likesService.removeLike(likesDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
