package com.sportsmania.swith.Service;

import com.sportsmania.swith.DTO.UserDto;
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

    @Override
    public UserDto findByUsername(String username) {
        UserDto dto = modelMapper.map(userMapper.findByUserId(username),UserDto.class);
        return dto;
    }
}
