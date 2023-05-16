package com.sportsmania.swith.mapper;

import com.sportsmania.swith.domain.ChatroomsVO;
import com.sportsmania.swith.dto.ChatroomsDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ChatMapper {
    List<ChatroomsVO> selectChatrooms(String nickname);
    int selectMembers(String roomTitle);
    void deleteChatroom(String team_title);
}
