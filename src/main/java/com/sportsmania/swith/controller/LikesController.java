package com.sportsmania.swith.controller;

import com.sportsmania.swith.dto.LikesDTO;
import com.sportsmania.swith.service.LikesService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stories/posts/likes")
public class LikesController {

    @Autowired
    private  final LikesService likesService;


    public LikesController(LikesService likesService) {
        this.likesService = likesService;
    }

    @PostMapping("/")
    public ResponseEntity addLike(@RequestBody LikesDTO likesDTO){
            likesService.addLike(likesDTO);
            return new ResponseEntity(HttpStatus.CREATED);
    }

}
