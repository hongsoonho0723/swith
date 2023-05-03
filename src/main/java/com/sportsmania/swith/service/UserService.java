package com.sportsmania.swith.service;

import com.sportsmania.swith.dto.UserDTO;


public interface UserService {
    void join(UserDTO dto);

    UserDTO login(String userId, String Pwd);
    int modify(UserDTO dto);

    UserDTO findByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsBynickname(String nickname); // 닉네임 중복체크

    boolean checkDuplicateId(String userId); // 아이디 중복체크
}
