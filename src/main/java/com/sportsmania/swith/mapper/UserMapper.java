package com.sportsmania.swith.mapper;

import com.sportsmania.swith.dto.UserDTO;
import com.sportsmania.swith.domain.UserVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    void join(UserDTO dto);

    UserDTO findByIdPW(@Param("userId") String userId, @Param("Pwd") String Pwd);

    int modifyInfo(UserDTO dto);

    UserVO findByUserId(String userId);
}
