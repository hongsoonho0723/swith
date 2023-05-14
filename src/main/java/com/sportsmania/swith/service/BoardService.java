package com.sportsmania.swith.service;

import com.sportsmania.swith.dto.BoardDTO;
import com.sportsmania.swith.dto.PageRequestDTO;
import com.sportsmania.swith.dto.PageResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;


public interface BoardService {
    void register(BoardDTO boardDTO);
    PageResponseDTO<BoardDTO> getList(PageRequestDTO pageRequestDTO);
    List<BoardDTO> mainList(BoardDTO boardDTO);
    int getaddtionNum(String roomtitle);
}
