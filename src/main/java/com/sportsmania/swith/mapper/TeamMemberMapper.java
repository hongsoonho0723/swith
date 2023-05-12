package com.sportsmania.swith.mapper;

import com.sportsmania.swith.domain.TeamMemberVO;
import com.sportsmania.swith.dto.TeamMemberDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TeamMemberMapper {
   void insert(TeamMemberVO teamMemberVO);

    void deleteMember(TeamMemberVO teamMemberVO);

    void deleteTeam(String team_title);

    List<TeamMemberVO> selectUser(String team_title);

    List<TeamMemberVO> selectMember(String team_title);

    List<TeamMemberVO> selectAll(String team_title);

    List<TeamMemberVO> selectAllMember();

    void update(TeamMemberVO teamMemberVO);



}
