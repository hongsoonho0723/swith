package com.sportsmania.swith.service;

import com.sportsmania.swith.dto.MatchBoardDTO;
import com.sportsmania.swith.dto.PageRequestDTO;
import com.sportsmania.swith.dto.PageResponseDTO;

import java.util.List;

public interface MatchBoardService {
  //  List<MatchBoardDTO> getAll();

    void register(MatchBoardDTO matchBoardDTO);

    void modify(MatchBoardDTO matchBoardDTO);

    MatchBoardDTO getOne(int board_no);

    void remove(int board_no);



    PageResponseDTO<MatchBoardDTO> getList(PageRequestDTO pageRequestDTO);

}
