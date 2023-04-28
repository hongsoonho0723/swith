package com.sportsmania.swith.Service;

import com.sportsmania.swith.DTO.UserDTO;


public interface UserService {
    void join(UserDTO dto);

    UserDTO login(String userId,String Pwd);
    int modify(UserDTO dto);
}
