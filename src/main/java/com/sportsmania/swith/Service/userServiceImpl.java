package com.sportsmania.swith.Service;

import com.sportsmania.swith.DTO.UserDto;
import com.sportsmania.swith.mapper.UserMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@Log4j2
public class userServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;


    @Override
    public void join(UserDto dto) {
        userMapper.join(dto);
    }

    @Override
    public UserDto login(String userId, String Pwd) {
        return userMapper.findByIdPW(userId,Pwd);
    }

    @Override
    public int modify(UserDto dto) {
        return userMapper.modifyInfo(dto);
    }
}
