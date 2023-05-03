package com.sportsmania.swith.mapper;


import com.sportsmania.swith.dto.BoardJjimDTO;
import com.sportsmania.swith.dto.PageRequestDTO;

public interface BoardJjimMapper {
    void Jjiminsert(BoardJjimDTO boardJjimDTO);
    int JjimCount(PageRequestDTO pageRequestDTO);
}