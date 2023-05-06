package com.sportsmania.swith.mapper;

import com.sportsmania.swith.domain.SupportTeamVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Mapper
public interface SupportTeamMapper {
    void insert(SupportTeamVO supportTeamVO);
    List<SupportTeamVO> selectAll();
    SupportTeamVO selectOne(String team_title);
    void update(SupportTeamVO supportTeamVO);
    void delete(String team_title);

    void insertWithFile(SupportTeamVO supportTeamVO, MultipartFile file);
}
