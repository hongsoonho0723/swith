package com.sportsmania.swith.service;

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

    void modify(TeamMemberDTO teamMemberDTO);
}
