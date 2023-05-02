package com.sportsmania.swith.service;

import com.sportsmania.swith.dto.UserDTO;


public interface UserService {
    void join(UserDTO dto);

    UserDTO login(String userId, String Pwd);
    int modify(UserDTO dto);

    UserDTO findByUsername(String username);
}
