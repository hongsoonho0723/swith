package com.sportsmania.swith.Service;

import com.sportsmania.swith.DTO.UserDTO;
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
    public void join(UserDTO dto) {
        userMapper.join(dto);
    }

    @Override
    public UserDTO login(String userId, String Pwd) {
        return userMapper.findByIdPW(userId,Pwd);
    }

    @Override
    public int modify(UserDTO dto) {
        return userMapper.modifyInfo(dto);
    }
}
