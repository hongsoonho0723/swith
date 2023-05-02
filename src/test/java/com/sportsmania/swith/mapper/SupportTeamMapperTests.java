package com.sportsmania.swith.mapper;

import com.sportsmania.swith.dto.SupportTeamDTO;
import com.sportsmania.swith.service.SupportTeamService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Log4j2
public class SupportTeamMapperTests {
    @Autowired
    private SupportTeamService supportTeamService;

    @Test
    public void insert() {
        SupportTeamDTO supportTeamDTO2 = SupportTeamDTO.builder()
                .team_title("testTeam2")
                .team_writer("testUser2")
                .content("testContent2")
                .sido("부산")
                .sigungu("금정구")
                .member_num(4)
                .regdate("2023-09-10 18:20:15")
                .image_team("C:/User/momentum")
                .simple_content("hello, we are volunteers.")
                .inquiry("채팅방으로 오셈")
                .finished(false)
                .deadline("2023-10-19")
                .build();
        log.info(supportTeamDTO2);
        supportTeamService.register(supportTeamDTO2);
    }

    @Test
    public void update(){
        SupportTeamDTO supportTeamDTO = SupportTeamDTO.builder()
                .team_title("testTeam1")
                .team_writer("testUser1")
                .content("testContent1")
                .sido("서울")
                .sigungu("노원구")
                .member_num(5)
                .regdate("2023-10-10 23:40:15")
                .image_team("C:/User/momentum")
                .simple_content("hello, we are volunteers.")
                .inquiry("카톡말고 인스타로 문의주셈")
                .finished(false)
                .deadline("2023-10-13")
                .build();
        log.info(supportTeamDTO);
        supportTeamService.modify(supportTeamDTO);
    }

    @Test
    public void getAll(){
        List<SupportTeamDTO> list = supportTeamService.getAll();
        list.stream().forEach(dto -> log.info(dto.getRegdate()));
    }

    @Test
    public void getOne(){
        SupportTeamDTO supportTeamDTO = supportTeamService.getOne("testTeam2");
        log.info(supportTeamDTO);
    }

    @Test
    public void remove(){
        supportTeamService.remove("testTeam2");
    }


}
