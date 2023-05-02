package com.sportsmania.swith.controller;

import com.sportsmania.swith.dto.SupportTeamDTO;
import com.sportsmania.swith.service.SupportTeamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@Log4j2
@RequiredArgsConstructor
public class SupportTeamController {

    private final SupportTeamService supportTeamService;


    @GetMapping("/teams/posts")
    public ModelAndView viewResgister(){
        ModelAndView mv = new ModelAndView("/teams/sp-register");
        return mv;
    }

    @PostMapping("/teams/posts")
    public ResponseEntity registerPosts(@RequestBody SupportTeamDTO supportTeamDTO){
        log.info("dto = " + supportTeamDTO);
        String deadline = supportTeamDTO.getDeadline();
        supportTeamDTO.setDeadline(deadline.replaceAll("T", " "));
        log.info("dto.deadline" + supportTeamDTO.getDeadline());

        supportTeamDTO.setTeam_writer("testUser1");
        supportTeamDTO.setImage_team("imagefileTest1");
        supportTeamService.register(supportTeamDTO);
        return new ResponseEntity<>(supportTeamDTO, HttpStatus.OK);
    }

    @GetMapping("teams")
    public ResponseEntity viewList() {
        List<SupportTeamDTO> dtoList = supportTeamService.getAll();
        log.info("teams view Controller 작동완료");
        return new ResponseEntity(dtoList, HttpStatus.OK);
    }

    @GetMapping("teams/{team_title}")
    public ResponseEntity viewPost(@PathVariable String team_title){
        SupportTeamDTO supportTeamDTO = supportTeamService.getOne(team_title);
        log.info("list view" + supportTeamDTO);
        return new ResponseEntity<>(supportTeamDTO, HttpStatus.OK);
    }

   @Controller
    class viewController{
        @GetMapping("/teams/list")
       public String teamsView(){
            return "/teams/sp-list";
        }
   }




}
