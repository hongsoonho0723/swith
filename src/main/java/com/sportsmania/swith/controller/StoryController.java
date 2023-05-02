package com.sportsmania.swith.controller;

import com.sportsmania.swith.dto.PageRequestDTO;
import com.sportsmania.swith.dto.StoryDTO;
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

import javax.validation.Valid;
import java.io.IOException;


@Controller
@Log4j2
public class StoryController {

    @Autowired
    private final StoryService storyService;


    public StoryController(StoryService storyService) {
        this.storyService = storyService;
    }


    @GetMapping("stories/posts")
    public String getRegister() {

        return "/story/register";
    }


    /*@GetMapping("/posts")
    public ResponseEntity<String> getRegisterPage() throws IOException {
        // ResourceLoader를 이용하여 register.html 파일을 가져옵니다.
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource resource = resourceLoader.getResource("classpath:/story/register.html");
        String html = new String(Files.readAllBytes(resource.getFile().toPath()));

        // 응답 생성
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_HTML);
        return new ResponseEntity<String>(html, headers, HttpStatus.OK);
    }*/

    @RestController
    public  class StoryRestController {
        @PostMapping(value = "stories/posts")
        public ResponseEntity<StoryDTO> registerPOST(@Valid @RequestBody StoryDTO storyDTO) throws IOException {
            log.info("registerPOST");

            storyService.register(storyDTO);

            return new ResponseEntity<>(storyDTO, HttpStatus.OK);
        }

        @DeleteMapping("/stories/posts/{story_no}")
        public ResponseEntity<Void> deleteStory(@PathVariable("story_no") Long story_no) {
            storyService.remove(story_no);

            return ResponseEntity.noContent().build(); //HTTP 응답 코드 204
        }

        @PutMapping("/stories/posts/{story_no}")
        public ResponseEntity<StoryDTO> modifyStory(@RequestBody StoryDTO storyDTO) {
            storyService.modify(storyDTO);

            return ResponseEntity.noContent().build(); //HTTP 응답 코드 204
        }

        /*@GetMapping(value = "/stories/{story_no}")
        public ResponseEntity<StoryDTO> read(@PathVariable Long story_no) {
            StoryDTO storyDTO = storyService.getOne(story_no);
            log.info(storyDTO);
            return ResponseEntity.ok(storyDTO);
        }*/

       /* @GetMapping("/stories/{story_no}")
        public ResponseEntity<StoryDTO> getStory(@PathVariable Long story_no) {
            StoryDTO storyDTO = storyService.getOne(story_no);
            if (storyDTO == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(storyDTO);
        }*/
    }
   /* @RequestMapping(value = "/stories/posts", method =RequestMethod.POST)
    public String registerPOST(@Valid StoryDTO storyDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) throws IOException {
        log.info("registerPOST");

        storyService.register(storyDTO);

        return "redirect: /stories";
    }*/



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
            return "story/read";
        }

}