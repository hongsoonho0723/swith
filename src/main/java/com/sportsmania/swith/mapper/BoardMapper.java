package com.sportsmania.swith.mapper;

import com.sportsmania.swith.dto.BoardDTO;
import com.sportsmania.swith.dto.PageRequestDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {
    void insert(BoardDTO boardDTO); //9개 넣어야함, 그중 writer은 외래키라서 작성자가 테이블에 있어야 들어감
    String getTime();
    List<BoardDTO> mainList(BoardDTO boardDTO);
    List<BoardDTO> selectList(PageRequestDTO pageRequestDTO);
    int getCount(PageRequestDTO pageRequestDTO);
//    int countAdditionalNum();
}
