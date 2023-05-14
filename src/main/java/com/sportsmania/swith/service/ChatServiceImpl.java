package com.sportsmania.swith.service;

import com.sportsmania.swith.domain.ChatroomsVO;
import com.sportsmania.swith.dto.ChatroomsDTO;
import com.sportsmania.swith.mapper.ChatMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {
    private final ModelMapper modelMapper;
    private final ChatMapper chatMapper;
    @Override
    public List<ChatroomsDTO> getChatrooms(String nickname) {
        List<ChatroomsDTO> dtoList = chatMapper.selectChatrooms(nickname).stream()
                .map(vo->modelMapper.map(vo,ChatroomsDTO.class))
                .collect(Collectors.toList());
        log.info("chatrooms :"+ dtoList);
        return dtoList;
    }
}
