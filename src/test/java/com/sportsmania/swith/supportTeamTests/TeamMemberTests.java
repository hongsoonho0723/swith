package com.sportsmania.swith.supportTeamTests;

import com.sportsmania.swith.dto.TeamMemberDTO;
import com.sportsmania.swith.service.TeamMemberService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Log4j2
public class TeamMemberTests {
    @Autowired
    private TeamMemberService teamMemberService;

    @Test
    public  void insert(){
        TeamMemberDTO teamMemberDTO = TeamMemberDTO.builder()
                .team_title("testTeam1")
                .team_memberId("testUser6")
                .team_fixed(false)
                .build();
        teamMemberService.register(teamMemberDTO);
    }

    @Test
    public void update(){
        TeamMemberDTO teamMemberDTO = TeamMemberDTO.builder()
                .team_title("testTeam1")
                .team_memberId("testUser4")
                .team_fixed(false)
                .build();
        teamMemberService.modify(teamMemberDTO);
    }

    @Test
    public void getMember(){
        String team_title = "testTeam1";
        List<TeamMemberDTO> dtoList = teamMemberService.getMember(team_title);
        dtoList.stream().forEach(dto -> log.info(dto));
    }

    @Test
    public void getUser(){
        String team_title = "testTeam1";
        List<TeamMemberDTO> userList = teamMemberService.getUser(team_title);
        userList.stream().forEach(dto -> log.info(dto));
    }

    @Test
    public void removeMember(){
        TeamMemberDTO teamMemberDTO = TeamMemberDTO.builder()
                .team_title("testTeam1")
                .team_memberId("testUser4")
                .team_fixed(false)
                .build();
        teamMemberService.removeMember(teamMemberDTO);
    }

    @Test void removeTeam(){
        String team_title = "testTeam2";
        teamMemberService.removeTeam(team_title);
    }

}
