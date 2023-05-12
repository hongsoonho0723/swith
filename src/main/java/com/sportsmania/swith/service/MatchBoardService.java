package com.sportsmania.swith.service;

import com.sportsmania.swith.dto.MatchBoardDTO;
import com.sportsmania.swith.dto.UserDTO;

import java.util.List;

public interface MatchBoardService {
  //  List<MatchBoardDTO> getAll();

    void register(MatchBoardDTO matchBoardDTO);

    void modify(MatchBoardDTO matchBoardDTO);

    void modify1(MatchBoardDTO matchBoardDTO);
    MatchBoardDTO getOne(int board_no);
    UserDTO getOne1(String userId);

    void remove(int board_no);



}
