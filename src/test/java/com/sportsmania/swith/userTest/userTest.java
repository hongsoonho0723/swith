package com.sportsmania.swith.userTest;

import com.sportsmania.swith.DTO.userDTO;
import com.sportsmania.swith.Service.userServiceImpl;
import com.sportsmania.swith.mapper.userMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
public class userTest {

    @Autowired
    private userServiceImpl userService;

    @Test
    public void join(){
        userDTO dto = userDTO.builder()
                .userId("rbals")
                .name("규민")
                .pwd("1234")
                .nickname("dsad")
                .birthday(LocalDate.parse("1998-03-26"))
                .phone("01091770961")
                .email("rbals@rbas")
                .joinType("1")
                .build();
        userService.join(dto);
    }
}
