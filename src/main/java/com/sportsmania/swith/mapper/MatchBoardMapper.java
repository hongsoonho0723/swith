package com.sportsmania.swith.mapper;

import com.sportsmania.swith.domain.MatchBoardVO;
import com.sportsmania.swith.domain.UserVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MatchBoardMapper {


  /*  static List<MatchBoardVO> selectAll() ;*/

    void insert(MatchBoardVO matchBoardVO);
    void update1(MatchBoardVO matchBoardVO);
    void update(MatchBoardVO matchBoardVO);
    MatchBoardVO selectOne(int board_no);
    UserVO selectOne1(String userId);
    void delete(int board_no);
    void increaseViewCount(int board_no);



}
