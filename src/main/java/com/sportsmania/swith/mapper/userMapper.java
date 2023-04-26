package com.sportsmania.swith.mapper;

import com.sportsmania.swith.DTO.userDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface userMapper {
    void join(userDTO dto);
}
