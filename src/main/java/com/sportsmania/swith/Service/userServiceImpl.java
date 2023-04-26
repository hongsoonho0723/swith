package com.sportsmania.swith.Service;

import com.sportsmania.swith.DTO.userDTO;
import com.sportsmania.swith.mapper.userMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@Log4j2
public class userServiceImpl implements userService{
    @Autowired
    private userMapper userMapper;


    @Override
    public void join(userDTO dto) {
        userMapper.join(dto);
    }
}
