package com.sportsmania.swith.service;

import com.sportsmania.swith.dto.ChatroomsDTO;

import java.util.List;

public interface ChatService {
    List<ChatroomsDTO> getChatrooms(String nickname);
}
