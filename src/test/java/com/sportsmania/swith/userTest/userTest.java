package com.sportsmania.swith.userTest;

import com.sportsmania.swith.DTO.UserDto;
import com.sportsmania.swith.Service.userServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Optional;

@SpringBootTest
public class userTest {

    @Autowired
    private userServiceImpl userService;

    @Test
    public void join(){
        UserDto dto = UserDto.builder()
                .userId("rbalss")
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

    @Test
    public void login(){
        String id = "rbal";
        String pwd = "1234";
        Optional<UserDto> result = Optional.ofNullable(userService.login(id, pwd));
        System.out.println(result);

    }
}
