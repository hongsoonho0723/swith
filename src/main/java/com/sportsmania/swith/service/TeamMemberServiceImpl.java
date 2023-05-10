package com.sportsmania.swith.service;

import com.sportsmania.swith.domain.TeamMemberVO;
import com.sportsmania.swith.dto.TeamMemberDTO;
import com.sportsmania.swith.mapper.TeamMemberMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class TeamMemberServiceImpl implements TeamMemberService{
    private final ModelMapper modelMapper;
    private final TeamMemberMapper teamMemberMapper;


    @Override
    public void register(TeamMemberDTO teamMemberDTO) {
        log.info("regi-TeamMemberDTO: " + teamMemberDTO);
        TeamMemberVO teamMemberVO = modelMapper.map(teamMemberDTO, TeamMemberVO.class);
        log.info("regi-TeamMemberVO: " + teamMemberVO);
        teamMemberMapper.insert(teamMemberVO);
    }

    @Override
    public void removeMember(TeamMemberDTO teamMemberDTO) {
        log.info("remove-TeamMemberDTO: " + teamMemberDTO);
        TeamMemberVO teamMemberVO = modelMapper.map(teamMemberDTO, TeamMemberVO.class);
        log.info("remove-TeamMemberVO: " + teamMemberVO);
        teamMemberMapper.deleteMember(teamMemberVO);
    }

    @Override
    public void removeTeam(String team_title) {
        log.info("removeTeam_title: " + team_title);
        teamMemberMapper.deleteTeam(team_title);
    }

    @Override
    public List<TeamMemberDTO> getUser(String team_title) {
       List<TeamMemberDTO> userList = teamMemberMapper.selectUser(team_title).stream()
               .map(vo -> modelMapper.map(vo,TeamMemberDTO.class))
               .collect(Collectors.toList());
        return userList;
    }

    @Override
    public List<TeamMemberDTO> getMember(String team_title) {
        List<TeamMemberDTO> memberList = teamMemberMapper.selectMember(team_title).stream()
                .map(vo -> modelMapper.map(vo,TeamMemberDTO.class))
                .collect(Collectors.toList());
        return memberList;
    }

    @Override
    public List<TeamMemberDTO> getAll(String team_title) {
        List<TeamMemberDTO> teamList = teamMemberMapper.selectAll(team_title).stream()
                .map(vo -> modelMapper.map(vo, TeamMemberDTO.class))
                .collect(Collectors.toList());
        return teamList;
    }

    @Override
    public List<TeamMemberDTO> getAllMember() {
        List<TeamMemberDTO> memberList = teamMemberMapper.selectAllMember().stream()
                .map(vo -> modelMapper.map(vo, TeamMemberDTO.class))
                .collect(Collectors.toList());
        return memberList;
    }

    @Override
    public void modify(TeamMemberDTO teamMemberDTO) {
        TeamMemberVO teamMemberVO = modelMapper.map(teamMemberDTO, TeamMemberVO.class);
        teamMemberMapper.update(teamMemberVO);
    }


}
