package com.sportsmania.swith.mapper;

import com.sportsmania.swith.domain.ChatroomVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ChatroomMapper {
    String getTime();
    void insert(ChatroomVO chatroomVO);
}
