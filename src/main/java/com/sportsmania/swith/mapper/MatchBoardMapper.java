package com.sportsmania.swith.mapper;

import com.sportsmania.swith.domain.MatchBoardVO;
import com.sportsmania.swith.dto.MatchBoardDTO;
import com.sportsmania.swith.dto.PageRequestDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MatchBoardMapper {


  /*  static List<MatchBoardVO> selectAll() ;*/

    void insert(MatchBoardVO matchBoardVO);

    void update(MatchBoardVO matchBoardVO);
    MatchBoardVO selectOne(int board_no);

    void delete(int board_no);

    List<MatchBoardDTO> selectList(PageRequestDTO pageRequestDTO);
    int getCount(PageRequestDTO pageRequestDTO);



}
