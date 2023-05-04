package com.sportsmania.swith.mapper;


import com.sportsmania.swith.dto.BoardJjimDTO;
import com.sportsmania.swith.dto.PageRequestDTO;

import java.util.List;
import java.util.Map;

public interface BoardJjimMapper {
    //찜추가
    public void insertJjim(Map<String, Integer> map);
    //찜삭제
    public void deleteJjim(Map<String, Integer> map);
    //?
    public int jjimCount(PageRequestDTO pageRequestDTO);
    //찜목록확인
    public List<BoardJjimDTO> jjimListByUserid(String userid);


}