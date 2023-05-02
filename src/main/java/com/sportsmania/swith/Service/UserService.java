package com.sportsmania.swith.Service;

import com.sportsmania.swith.DTO.UserDto;


public interface UserService {
    void join(UserDto dto);

    UserDto login(String userId,String Pwd);
    int modify(UserDto dto);

    UserDto findByUsername(String username);
}
