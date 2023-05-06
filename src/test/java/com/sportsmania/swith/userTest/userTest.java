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
import java.util.stream.IntStream;

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
    public void signup(){
        IntStream.rangeClosed(10,30).forEach(i ->{
            UserDTO userDTO = UserDTO.builder()
                    .userId("testUSer" + i)
                    .pwd("1111" + i)
                    .email(i + "ex@naver.com")
                    .birthday("1997-03-" + i)
                    .nickname("testNick" + i)
                    .phone("010-9999-99" + i)
                    .name("이테스트" + i)
                    .joinType("1")
                    .auth("ROLE_USER")
                    .build();
            userService.join(userDTO);
        });
    }

    @Test
    public void login(){
        String id = "rbal";
        String pwd = "1234";
        Optional<UserDTO> result = Optional.ofNullable(userService.login(id, pwd));
        System.out.println(result);

    }
    @Test
    public void testt(){
        UserVO userVo = userMapper.findByUserId("rbalswkd12");
        UserDTO dto = modelMapper.map(userVo, UserDTO.class);
        System.out.println(dto.getName());
    }
}
