package com.sportsmania.swith.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.sportsmania.swith.dto.PageRequestDTO;
import com.sportsmania.swith.dto.ReplyDTO;
import com.sportsmania.swith.dto.StoryDTO;
import com.sportsmania.swith.service.ReplyService;
import com.sportsmania.swith.service.StoryService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.stream.IntStream;


@Controller
@Log4j2
public class StoryController {

    @Autowired
    private final StoryService storyService;

    @Autowired
    private final ReplyService replyService;

    public StoryController(StoryService storyService, ReplyService replyService) {
        this.storyService = storyService;
        this.replyService = replyService;
    }


    @GetMapping("/stories/posts")
    public String getRegister() {

        return "/story/register";
    }

    @RestController
    public  class StoryRestController {
       /* @PostMapping(value = "stories/posts")
        public ResponseEntity<StoryDTO> registerPOST(@Valid @RequestBody StoryDTO storyDTO) throws IOException {
            log.info("registerPOST");
            log.info(storyDTO);
            storyService.register(storyDTO);

            return new ResponseEntity<>(storyDTO, HttpStatus.OK);
        }*/
        @PostMapping("/stories/posts")
        public ResponseEntity registerPOST(@RequestParam("image") MultipartFile file, @ModelAttribute StoryDTO storyDTO) throws IOException {

            storyService.uploadStoryFile(storyDTO, file);

            return new ResponseEntity(storyDTO, HttpStatus.OK);
        }

        @DeleteMapping("/stories/posts/{story_no}")
        public ResponseEntity<Void> deleteStory(@PathVariable("story_no") Long story_no) {
            List<ReplyDTO> replyList = replyService.getList(story_no);
            log.info(replyList);
            if(!replyList.isEmpty()){
                IntStream.rangeClosed(0, replyList.size()-1).forEach(i ->{
                    ReplyDTO replyDTO = replyList.get(i);
                    replyService.remove(replyDTO.getReply_no());
                });
            }
            log.info("스토리 삭제: " + story_no);
            storyService.remove(story_no);

            return ResponseEntity.noContent().build(); //HTTP 응답 코드 204
        }

        @PutMapping("/stories/posts/{story_no}")
        public ResponseEntity<StoryDTO> modifyStory(@RequestBody StoryDTO storyDTO) {
            storyService.modify(storyDTO);

            return ResponseEntity.noContent().build(); //HTTP 응답 코드 204
        }


    }

        @RequestMapping(value = "/stories", method = RequestMethod.GET)
        public String list(@Valid PageRequestDTO pageRequestDTO, BindingResult bindingResult, Model model) {
            log.info(pageRequestDTO);
            if (bindingResult.hasErrors()) {
                pageRequestDTO = PageRequestDTO.builder().build();
            }
            model.addAttribute("responseDTO", storyService.getList(pageRequestDTO));

            return "/story/list";
        }
        @GetMapping(value = "/stories/{story_no}")
        public String read(@PathVariable("story_no") Long story_no, Model model) {
            StoryDTO storyDTO = storyService.getOne(story_no);
            log.info(storyDTO);

            model.addAttribute("dto", storyDTO);
            return "/story/read";
        }
}