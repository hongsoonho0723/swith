package com.sportsmania.swith.controller;

import com.sportsmania.swith.dto.SupportTeamDTO;
import com.sportsmania.swith.dto.TeamMemberDTO;
import com.sportsmania.swith.service.SupportTeamService;
import com.sportsmania.swith.service.TeamMemberService;
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
    private final TeamMemberService teamMemberService;

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

    @GetMapping("/teams")
    public ModelAndView viewList() {
        List<SupportTeamDTO> dtoList = supportTeamService.getAll();
        ModelAndView mv = new ModelAndView("/teams/sp-list");
        log.info("teams view Controller 작동완료");
        mv.addObject("dtoList", dtoList);
        return mv;
    }

    @GetMapping("teams/{team_title}")
    public ModelAndView viewPost(@PathVariable("team_title") String team_title){
        SupportTeamDTO supportTeamDTO = supportTeamService.getOne(team_title);
        log.info("list view" + supportTeamDTO);
        ModelAndView mv = new ModelAndView("/teams/sp-view");
        mv.addObject("dto", supportTeamDTO);
        return mv;
    }

    @GetMapping("teams//{team_title}/{team_fixed}")  //teams/members/1
    public ResponseEntity transferViewData(@PathVariable("team_title") String team_title,
                                           @PathVariable("team_fixed") int team_fixed) {
        if(team_fixed == 1) {
            List<TeamMemberDTO> memberList = teamMemberService.getMember(team_title);
            log.info(memberList);
            return new ResponseEntity<>(memberList, HttpStatus.OK);
        } else {
            List<TeamMemberDTO> userList = teamMemberService.getUser(team_title);
            log.info(userList);
            return new ResponseEntity<>(userList, HttpStatus.OK);
        }
    }

}
