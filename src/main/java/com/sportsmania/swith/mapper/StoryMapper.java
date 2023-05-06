package com.sportsmania.swith.mapper;

import com.sportsmania.swith.domain.StoryFileVO;
import com.sportsmania.swith.dto.PageRequestDTO;
import com.sportsmania.swith.domain.StoryVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StoryMapper {

    //테스트
    String getTime();

    void insert (StoryVO storyVO);

    StoryVO selectOne(Long story_no);

    void delete(Long story_no);

    void increaseViewCount(Long storyNo);

    void  likeCount(Long story_no);

    void update(StoryVO storyVO);

    List<StoryVO> selectList(PageRequestDTO pageRequestDTO);

    int getCount(PageRequestDTO pageRequestDTO);

    //이미지 처리
    @Insert("INSERT INTO story_file values(#{filename}, #{story_no}")
    void insertFile(StoryFileVO storyFileVO);

}
