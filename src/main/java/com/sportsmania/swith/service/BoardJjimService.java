package com.sportsmania.swith.service;

import com.sportsmania.swith.dto.BoardJjimDTO;

import java.util.List;

public interface BoardJjimService {
    //추가
    public void register(BoardJjimDTO boardJjimDTO);
    //찜목록확인
//    public List<BoardJjimDTO> getJjimListByUserid(String userid);
    //삭제
    public void remove(BoardJjimDTO boardJjimDTO);

    // 특정 게시물에 대한 찜 여부 체크
    boolean isWishByUser(int board_no, String userid);

}
