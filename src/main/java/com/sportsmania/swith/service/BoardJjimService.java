package com.sportsmania.swith.service;

import com.sportsmania.swith.dto.BoardJjimDTO;

import java.util.List;

public interface BoardJjimService {
    //추가
    public void register(BoardJjimDTO boardJjimDTO);
    //찜목록확인
    public List<BoardJjimDTO> getJjimListByUserid(String userid);
    //삭제
    public void remove(int board_no,String userid);
    //이미찜한게시물인지 조회
    public BoardJjimDTO getByBnoWithUserid(BoardJjimDTO boardJjimDTO);
}
