package com.sportsmania.swith.service;

import com.sportsmania.swith.domain.UserVO;
import com.sportsmania.swith.dto.BoardDTO;
import com.sportsmania.swith.dto.UserDTO;
import com.sportsmania.swith.dto.WishDTO;
import com.sportsmania.swith.dto.blackDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface UserService {
    void join(UserDTO dto);

    UserDTO login(String userId, String Pwd);
    int modify(UserDTO dto);

    UserDTO findByUsername(String username);

    UserDTO findByUserNickname(String nickname);

    boolean existsByEmail(String email);

    boolean existsBynickname(String nickname); // 닉네임 중복체크

    boolean checkDuplicateId(String userId); // 아이디 중복체크

    void addAuth(UserDTO dto); //권한 추가

    UserVO userCheck(String name, String email); // 아이디 찾기

    UserVO findPwd(@Param("name") String name, @Param("email") String email, @Param("userId")String userId); // 비밀번호 찾기

    List<WishDTO> wish(String userId);

    List<blackDTO> blackList(String userId); // 블랙리스트

    List<BoardDTO> activeList(String nickname); // 활동내역

    void deleteUser(String userId); // 회원탈퇴

    void modifyPwd(UserDTO dto);
}
