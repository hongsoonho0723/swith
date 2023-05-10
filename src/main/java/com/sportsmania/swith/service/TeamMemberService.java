package com.sportsmania.swith.service;

import com.sportsmania.swith.domain.TeamMemberVO;
import com.sportsmania.swith.dto.StoryDTO;
import com.sportsmania.swith.dto.TeamMemberDTO;
import org.springframework.stereotype.Service;

import java.util.List;


public interface TeamMemberService {
    void register(TeamMemberDTO teamMemberDTO);

    void removeMember(TeamMemberDTO teamMemberDTO);

    void removeTeam(String team_title);

    List<TeamMemberDTO> getUser(String team_title);

    List<TeamMemberDTO> getMember(String team_title);
    List<TeamMemberDTO> getAll(String team_title);

    List<TeamMemberDTO> getAllMember(); //필요없는듯..ㅠㅠ

    List<StoryDTO> getUserTeams(String story_writer);

    List<StoryDTO> getTeamStories(String team_title);

    void modify(TeamMemberDTO teamMemberDTO);
}
