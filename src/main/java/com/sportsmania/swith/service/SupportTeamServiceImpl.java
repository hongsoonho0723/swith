package com.sportsmania.swith.service;

import com.sportsmania.swith.domain.SupportTeamVO;
import com.sportsmania.swith.dto.SupportTeamDTO;
import com.sportsmania.swith.mapper.SupportTeamMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class SupportTeamServiceImpl implements SupportTeamService  {
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

    @Override
    public void registerWithFile(SupportTeamDTO supportTeamDTO, MultipartFile file) throws IOException {
        String UPLOAD_DIR = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\assets\\image_team\\";
        String pathStr = "";
        String fileName = file.getOriginalFilename();
        String extension = fileName.substring(fileName.lastIndexOf("."));

        if(!file.isEmpty()){
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOAD_DIR + supportTeamDTO.getTeam_title() + extension);
            Files.write(path, bytes);
            pathStr = "../assets/image_team/" + supportTeamDTO.getTeam_title() + extension;
            supportTeamDTO.setImage_team(pathStr);
        }

        SupportTeamVO supportTeamVO = modelMapper.map(supportTeamDTO, SupportTeamVO.class);
        supportTeamMapper.insert(supportTeamVO);
    }




}
