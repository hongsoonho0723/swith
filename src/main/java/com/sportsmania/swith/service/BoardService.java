package com.sportsmania.swith.service;

import com.sportsmania.swith.dto.BoardDTO;
import com.sportsmania.swith.dto.PageRequestDTO;
import com.sportsmania.swith.dto.PageResponseDTO;
import org.springframework.stereotype.Service;


public interface BoardService {
    void register(BoardDTO boardDTO);
    PageResponseDTO<BoardDTO> getList(PageRequestDTO pageRequestDTO);

}
