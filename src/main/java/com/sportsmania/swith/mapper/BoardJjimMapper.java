package com.sportsmania.swith.mapper;


import com.sportsmania.swith.dto.BoardJjimDTO;
import com.sportsmania.swith.dto.PageRequestDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
@Mapper
public interface BoardJjimMapper {
    //찜추가
    public void insertJjim(BoardJjimDTO boardJjimDTO);
    //찜삭제
    public void deleteJjim(BoardJjimDTO boardJjimDTO);
    //?
    public int jjimCount(PageRequestDTO pageRequestDTO);
    //찜목록확인
    public List<BoardJjimDTO> getJjimListByUserid(String userid);
    //게시물 삭제시 전체 삭제
    public void deleteAllByBoard_no(int board_no);
    // 특정 게시물에 대한 찜 여부 체크
    boolean isWishByUser(int board_no, String userid);


}