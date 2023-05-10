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
    public void deleteJjim( int board_no,String userid);
    //?
    public int jjimCount(PageRequestDTO pageRequestDTO);
    //찜목록확인
    public List<BoardJjimDTO> getJjimListByUserid(String userid);
    //게시물 삭제시 전체 삭제
    public void deleteAllByBoard_no(int board_no);


}