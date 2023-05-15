package com.sportsmania.swith.mapper;

import com.sportsmania.swith.dto.BoardDTO;
import com.sportsmania.swith.dto.UserDTO;
import com.sportsmania.swith.domain.UserVO;
import com.sportsmania.swith.dto.WishDTO;
import com.sportsmania.swith.dto.blackDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {
    void join(UserDTO dto);

    UserDTO findByIdPW(@Param("userId") String userId, @Param("Pwd") String Pwd);

    int modifyInfo(UserDTO dto);

    UserVO findByUserId(String userId);

    UserVO findByNickname(String nickname);

    boolean existsByEmail(String email); // 이메일 중복체크

    boolean existsBynickname(String nickname); // 닉네임 중복체크

    boolean checkDuplicateId(String userId); // 아이디 중복체크

    void addAuth(UserDTO dto); //권한 추가

    UserVO userCheck(@Param("name") String name,@Param("email") String email); // 아이디 찾기
    
    UserVO findPwd(@Param("name") String name,@Param("email") String email,@Param("userId")String userId); // 비밀번호 찾기

    List<WishDTO> wish(String userId); // 찜

    List<blackDTO> blackList(String userId); // 블랙리스트

    List<BoardDTO> activeList(String nickname);

}
