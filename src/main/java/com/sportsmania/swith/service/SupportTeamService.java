package com.sportsmania.swith.service;

import com.sportsmania.swith.dto.SupportTeamDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface SupportTeamService {
    void register(SupportTeamDTO supportTeamDTO);
    List<SupportTeamDTO> getAll();

    List<SupportTeamDTO> getSearch(Boolean category, String keyword);

    SupportTeamDTO getOne(String team_title);

    void remove(String team_title);

    void modify(SupportTeamDTO supportTeamDTO);

    void registerWithFile(SupportTeamDTO supportTeamDTO,MultipartFile file) throws IOException;
    public List<SupportTeamDTO> getPage(int rownum1);
}
