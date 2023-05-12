package com.sportsmania.swith.userTest;

import com.sportsmania.swith.dto.UserDTO;
import com.sportsmania.swith.dto.WishDTO;
import com.sportsmania.swith.service.BoardJjimService;
import com.sportsmania.swith.service.EmailService;
import com.sportsmania.swith.service.userServiceImpl;
import com.sportsmania.swith.domain.UserVO;
import com.sportsmania.swith.mapper.UserMapper;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.lang.model.element.Element;
import javax.mail.MessagingException;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class userTest {

    @Autowired
    private userServiceImpl userService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private EmailService emailService;

    @Autowired
    private CacheManager cacheManager;
    @Autowired
    private BoardJjimService boardJjimService;

    private ModelMapper modelMapper = new ModelMapper();

    @Test
    public void wishTest(){

        boolean isWish = boardJjimService.isWishByUser(3, "rbalswkd12");
        System.out.println(isWish);
    }

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

    @Test
    public void EmailTest() throws MessagingException {
        emailService.sendVerificationEmail("rbalswkd1@nate.com");
        Cache cache = cacheManager.getCache("verificationCodes");
        String key = "rbalswkd1@nate.com";

        System.out.println(cache.get(key,String.class));
    }

    @Test
    public void wish(){


    }
}
