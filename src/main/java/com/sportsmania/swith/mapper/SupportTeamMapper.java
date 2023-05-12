package com.sportsmania.swith.mapper;

import com.sportsmania.swith.domain.SupportTeamVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Mapper
public interface SupportTeamMapper {
    void insert(SupportTeamVO supportTeamVO);
    List<SupportTeamVO> selectAll();

    List<SupportTeamVO> selectSearch(Boolean finished, String sido);
    SupportTeamVO selectOne(String team_title);

    void update(SupportTeamVO supportTeamVO);

    void delete(String team_title);
    List<SupportTeamVO> selectPage(int rownum1);


    void insertWithFile(SupportTeamVO supportTeamVO, MultipartFile file);

    List<SupportTeamVO> selectDeadline();
    List<SupportTeamVO> selectRegion(String userRegion);
}
