package com.sportsmania.swith.service;

import com.sportsmania.swith.dto.UserDTO;
import com.sportsmania.swith.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@Log4j2
@RequiredArgsConstructor
public class userServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    private ModelMapper modelMapper = new ModelMapper();


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

    @Override
    public UserDTO findByUsername(String username) {
        UserDTO dto = modelMapper.map(userMapper.findByUserId(username), UserDTO.class);
        return dto;
    }

    @Override
    public boolean existsByEmail(String email) {
        return userMapper.existsByEmail(email);
    }

    @Override
    public boolean existsBynickname(String nickname) {
        return userMapper.existsBynickname(nickname);
    }

    @Override
    public boolean checkDuplicateId(String userId) {
        return userMapper.checkDuplicateId(userId);
    }

    @Override
    public void addAuth(UserDTO dto) {
        userMapper.addAuth(dto);
    }
}
