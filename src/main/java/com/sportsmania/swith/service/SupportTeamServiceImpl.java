package com.sportsmania.swith.service;

import com.sportsmania.swith.domain.SupportTeamVO;
import com.sportsmania.swith.dto.SupportTeamDTO;
import com.sportsmania.swith.mapper.SupportTeamMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class SupportTeamServiceImpl implements SupportTeamService {
    private final ModelMapper modelMapper;
    private final SupportTeamMapper supportTeamMapper;

    @Override
    public void register(SupportTeamDTO supportTeamDTO) {
        log.info("regi-supportTeamDto: " + supportTeamDTO);
        SupportTeamVO supportTeamVO = modelMapper.map(supportTeamDTO, SupportTeamVO.class);
        log.info("regi-supportTeamVO: " + supportTeamVO);
        supportTeamMapper.insert(supportTeamVO);
    }

    @Override
    public List<SupportTeamDTO> getAll() {
        List<SupportTeamDTO> dtoList = supportTeamMapper.selectAll().stream()
                .map(vo -> modelMapper.map(vo, SupportTeamDTO.class))
                .collect(Collectors.toList());
        return dtoList;
    }

    @Override
    public SupportTeamDTO getOne(String team_title) {
        SupportTeamVO supportTeamVO = supportTeamMapper.selectOne(team_title);
        log.info("getOne-VO: " + supportTeamVO);
        SupportTeamDTO supportTeamDTO = modelMapper.map(supportTeamVO, SupportTeamDTO.class);
        log.info("getOne-DTO: " + supportTeamDTO);
        return supportTeamDTO;
    }

    @Override
    public void remove(String team_title) {
        supportTeamMapper.delete(team_title);
    }

    @Override
    public void modify(SupportTeamDTO supportTeamDTO) {
        SupportTeamVO supportTeamVO = modelMapper.map(supportTeamDTO, SupportTeamVO.class);
        supportTeamMapper.update(supportTeamVO);
    }
}
