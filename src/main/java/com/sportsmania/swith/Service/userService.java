package com.sportsmania.swith.Service;

import com.sportsmania.swith.DTO.userDTO;
import com.sportsmania.swith.mapper.userMapper;
import org.springframework.stereotype.Service;


public interface userService {
    void join(userDTO dto);
}
