package com.sportsmania.swith.userTest;

import com.sportsmania.swith.dto.UserDTO;
import com.sportsmania.swith.service.userServiceImpl;
import com.sportsmania.swith.domain.UserVO;
import com.sportsmania.swith.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

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
        Optional<UserDTO> result = Optional.ofNullable(userService.login(id, pwd));
        System.out.println(result);

    }
    @Test
    public void testt(){
        UserDTO userDTO = userService.findByUsername("rbalswkd12");
        userDTO.setAuth("ROLE_USER,ROLE_ADMIN,ROLE_TEAM");

          userService.addAuth(userDTO);

    }
    @Test
    public void eamil(){
        String name = "규민";
        String email = "33333333";

        UserVO result = userService.userCheck("규민","33333333");
        System.out.println(result);
    }
}
