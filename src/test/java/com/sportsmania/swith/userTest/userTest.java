package com.sportsmania.swith.userTest;

import com.sportsmania.swith.DTO.UserDto;
import com.sportsmania.swith.Service.userServiceImpl;
import com.sportsmania.swith.domain.UserVo;
import com.sportsmania.swith.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Optional;

@SpringBootTest
public class userTest {

    @Autowired
    private userServiceImpl userService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private ModelMapper modelMapper = new ModelMapper();


    @Test
    public void login(){
        String id = "rbal";
        String pwd = "1234";
        Optional<UserDto> result = Optional.ofNullable(userService.login(id, pwd));
        System.out.println(result);

    }
    @Test
    public void testt(){
        UserVo userVo = userMapper.findByUserId("rbalswkd12");
        UserDto dto = modelMapper.map(userVo,UserDto.class);
        System.out.println(dto.getName());
    }
}
