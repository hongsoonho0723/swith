package com.sportsmania.swith.service;

import com.sportsmania.swith.dto.SupportTeamDTO;

import java.util.List;

public interface SupportTeamService {
    void register(SupportTeamDTO supportTeamDTO);
    List<SupportTeamDTO> getAll();

    SupportTeamDTO getOne(String team_title);

    void remove(String team_title);

    void modify(SupportTeamDTO supportTeamDTO);
}
