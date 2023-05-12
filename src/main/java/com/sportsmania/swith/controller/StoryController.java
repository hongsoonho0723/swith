package com.sportsmania.swith.controller;


import com.sportsmania.swith.domain.StoryVO;
import com.sportsmania.swith.dto.ReplyDTO;
import com.sportsmania.swith.dto.StoryDTO;
import com.sportsmania.swith.dto.SupportTeamDTO;
import com.sportsmania.swith.dto.page.StoryPageRequestDTO;
import com.sportsmania.swith.service.ReplyService;
import com.sportsmania.swith.service.StoryService;
import com.sportsmania.swith.service.TeamMemberService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;


@Controller
@Log4j2
public class StoryController {

    @Autowired
    private final StoryService storyService;

    @Autowired
    private final ReplyService replyService;


    public StoryController(StoryService storyService, ReplyService replyService, TeamMemberService teamMemberService) {
        this.storyService = storyService;
        this.replyService = replyService;
    }


    @GetMapping("/stories/posts")
    public String getRegister() {

        return "/story/register";
    }

    @GetMapping(value = "/stories/posts/{story_no}")
    public String getModify(@PathVariable("story_no") Long story_no,  Model model) {
        StoryDTO storyDTO = storyService.getOne(story_no);
        log.info(storyDTO);

        model.addAttribute("dto",storyDTO);

        return "story/modify";
    }



    @GetMapping("/stories")
    public String list(StoryPageRequestDTO storyPageRequestDTO, BindingResult bindingResult, Model model) {
        log.info(storyPageRequestDTO);
        if (bindingResult.hasErrors()) {
            storyPageRequestDTO = storyPageRequestDTO.builder().build();
        }
        model.addAttribute("responseDTO", storyService.getList(storyPageRequestDTO));

        return "/story/list";
    }

    @GetMapping(value = "/stories/{story_no}")
    public String read(@PathVariable("story_no") Long story_no, Model model, HttpSession session) {

        StoryDTO storyDTO = storyService.getOne(story_no);
        log.info(storyDTO);

        // 세션에 저장된 게시글 번호 리스트를 가져옵니다.
        List<Long> viewedStoryList = (List<Long>) session.getAttribute("viewedStoryList");
        if (viewedStoryList == null) {
            viewedStoryList = new ArrayList<>();
        }

        // 현재 게시글 번호가 세션에 저장된 리스트에 없으면 조회수를 증가합니다.
        if (!viewedStoryList.contains(story_no)) {
            storyService.increaseViewCount(story_no);
            viewedStoryList.add(story_no);
            session.setAttribute("viewedStoryList", viewedStoryList);
        }

        model.addAttribute("dto", storyDTO);


        return "/story/read";
    }


    @RestController
    public  class StoryRestController {
        @PostMapping("/stories/posts")
        public ResponseEntity registerPOST(@RequestParam("image") MultipartFile file, @ModelAttribute StoryDTO storyDTO, Authentication authentication) throws IOException {
            String user_id = authentication.getName();
            storyDTO.setStory_writer(user_id);
            storyService.registerWithFile(storyDTO, file);

            return new ResponseEntity(storyDTO, HttpStatus.OK);
        }

        @DeleteMapping("/stories/posts/{story_no}")
        public ResponseEntity<Void> deleteStory(@PathVariable("story_no") Long story_no) {
            List<ReplyDTO> replyList = replyService.getList(story_no);
            log.info(replyList);
            if (!replyList.isEmpty()) {
                IntStream.rangeClosed(0, replyList.size() - 1).forEach(i -> {
                    ReplyDTO replyDTO = replyList.get(i);
                    replyService.remove(replyDTO.getReply_no());
                });
            }
            log.info("스토리 삭제: " + story_no);
            storyService.remove(story_no);

            return ResponseEntity.noContent().build();
        }

        @PreAuthorize("principal.username == #sotrydDTO.story_writer")
        @PutMapping("/stories/posts/{story_no}")
        public ResponseEntity<StoryDTO> modifyStory(@RequestParam("image") MultipartFile file,@RequestBody StoryDTO storyDTO, @PathVariable("story_no") Long story_no) throws IOException {
            storyDTO.setStory_no(story_no);
            storyService.modify(storyDTO, file);
            log.info(storyDTO);

            return new ResponseEntity<>(HttpStatus.OK);
        }

        @GetMapping("/stories/popular-stories")
        public ResponseEntity<List<StoryVO>> getPopularStories() {
           List<StoryVO> stories = storyService.getPopularStories();
            return new ResponseEntity<>(stories,HttpStatus.OK);
        }

    }


}