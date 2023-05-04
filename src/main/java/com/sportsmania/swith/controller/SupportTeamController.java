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
import java.util.stream.IntStream;

@RestController
@Log4j2
@RequiredArgsConstructor
public class SupportTeamController {

    private final SupportTeamService supportTeamService;
    private final TeamMemberService teamMemberService;

    @GetMapping("/teams/posts")
    public ModelAndView viewResgister() {
        ModelAndView mv = new ModelAndView("/teams/sp-register");

        return mv;
    }

    @PostMapping("/teams/posts")
    public ResponseEntity registerPosts(@RequestBody SupportTeamDTO supportTeamDTO) {
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

    @GetMapping("/teams/{team_title}")
    public ModelAndView viewPost(@PathVariable("team_title") String team_title) {
        SupportTeamDTO supportTeamDTO = supportTeamService.getOne(team_title);
        log.info("list view" + supportTeamDTO);
        ModelAndView mv = new ModelAndView("/teams/sp-view");
        mv.addObject("dto", supportTeamDTO);
        return mv;
    }

    @GetMapping("/teams/{team_title}/{team_fixed}")  //teams/members/1
    public ResponseEntity transferViewData(@PathVariable("team_title") String team_title,
                                           @PathVariable("team_fixed") int team_fixed) {
        if (team_fixed == 1) {
            List<TeamMemberDTO> memberList = teamMemberService.getMember(team_title);
            log.info(memberList);
            return new ResponseEntity<>(memberList, HttpStatus.OK);
        } else {
            log.info("team_fixed: " + team_fixed);
            List<TeamMemberDTO> userList = teamMemberService.getUser(team_title);
            log.info(userList);
            return new ResponseEntity<>(userList, HttpStatus.OK);
        }
    }

    @DeleteMapping("/teams/admin/{team_title}/{team_memberId}")
    public ResponseEntity rejectMember(@PathVariable("team_title") String team_title,
                                       @PathVariable("team_memberId") String team_memberId) {
        TeamMemberDTO teamMemberDTO = TeamMemberDTO.builder()
                .team_title(team_title)
                .team_memberId(team_memberId)
                .team_fixed(false)
                .build();
        teamMemberService.removeMember(teamMemberDTO);
        log.info("Controller : 멤버 요청 거절 service 실행");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/teams/admin/{team_title}/{team_memberId}")
    public ResponseEntity acceptMember(@PathVariable("team_title") String team_title,
                                       @PathVariable("team_memberId") String team_memberId) {
        TeamMemberDTO teamMemberDTO = TeamMemberDTO.builder()
                .team_title(team_title)
                .team_memberId(team_memberId)
                .team_fixed(true)
                .build();
        teamMemberService.modify(teamMemberDTO);
        log.info("Controller : 멤버 요청 수락 service 실행");
        return new ResponseEntity<>(teamMemberDTO, HttpStatus.OK);
    }

    @PostMapping("/teams/info")
    public ResponseEntity applicationTeam(@RequestBody TeamMemberDTO teamMemberDTO) {
        teamMemberDTO.setTeam_fixed(false);
        log.info("applicationTeam()의 dto: " + teamMemberDTO);
        teamMemberService.register(teamMemberDTO);
        return new ResponseEntity<>(teamMemberDTO, HttpStatus.OK);
    }

    @DeleteMapping("/teams/admin/{team_title}")
    public ResponseEntity removeTeam(@RequestBody SupportTeamDTO supportTeamDTO) {
        String team_title = supportTeamDTO.getTeam_title();
        List<TeamMemberDTO> teamList = teamMemberService.getAll(team_title);
        if (!teamList.isEmpty()) {
            IntStream.rangeClosed(0, teamList.size() - 1).forEach(i -> {
                TeamMemberDTO teamMemberDTO = teamList.get(i);
                teamMemberService.removeMember(teamMemberDTO);
            });
        }
        log.info("removeTeam()의 dto: " + supportTeamDTO);
        supportTeamService.remove(team_title);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/teams/total/{team_title}")
    public ResponseEntity recruitFinished(@RequestBody SupportTeamDTO supportTeamDTO) {
        SupportTeamDTO supportTeamDTO1 = supportTeamService.getOne(supportTeamDTO.getTeam_title());
        log.info("isFinished: " + supportTeamDTO.isFinished());
        if (supportTeamDTO.getInquiry() == null) {
            supportTeamDTO1.setFinished(supportTeamDTO.isFinished());
        }
        supportTeamService.modify(supportTeamDTO1);
        return new ResponseEntity<>(supportTeamDTO1, HttpStatus.OK);
    }

   /* @GetMapping("/teams/admin/{team_title}")
    public ResponseEntity viewModifyPage(@PathVariable("team_title") String team_title) {
        SupportTeamDTO supportTeamDTO = supportTeamService.getOne(team_title);
        ResponseEntity responseEntity;
        //if(userId.equals(supportTeamDTO.getTeam_writer)){
        responseEntity = new ResponseEntity(HttpStatus.OK);
        //}
        return responseEntity;
    }*/


    @GetMapping("/teams/admin/{team_title}")
    public ModelAndView viewModify(@PathVariable("team_title") String team_title) {
        SupportTeamDTO supportTeamDTO = supportTeamService.getOne(team_title);
        log.info("modify view" + supportTeamDTO);
        ModelAndView mv = new ModelAndView("/teams/sp-modify");
        mv.addObject("dto", supportTeamDTO);
        return mv;
    }

    @PutMapping("/teams/admin/{team_title}")
    public ResponseEntity modifyTeam(@RequestBody SupportTeamDTO supportTeamDTO,
                                     @PathVariable("team_title") String team_title) {
        String deadline = supportTeamDTO.getDeadline();
        supportTeamDTO.setDeadline(deadline.replaceAll("T", " "));
        supportTeamDTO.setTeam_title(team_title);
        log.info("modify dto: " + supportTeamDTO);

        supportTeamService.modify(supportTeamDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
