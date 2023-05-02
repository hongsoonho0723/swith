package com.sportsmania.swith.mapper;

import com.sportsmania.swith.DTO.UserDto;
import com.sportsmania.swith.domain.UserVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Mapper
public interface UserMapper {
    void join(UserDto dto);

    UserDto findByIdPW(@Param("userId") String userId,@Param("Pwd") String Pwd);

    int modifyInfo(UserDto dto);

    UserVo findByUserId(String userId);
}
