package com.sportsmania.swith.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MessageMapper {
    void deleteMessage(String team_title);
}
