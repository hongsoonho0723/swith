package com.sportsmania.swith.mapper;

import com.sportsmania.swith.domain.StoryFileVO;
import com.sportsmania.swith.domain.StoryVO;
import com.sportsmania.swith.dto.LikesDTO;
import com.sportsmania.swith.dto.page.StoryPageRequestDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface StoryMapper {


    void insert (StoryVO storyVO);

    StoryVO selectOne(Long story_no);

    void delete(Long story_no);

    void increaseViewCount(Long storyNo);

    void  likeCount(Long story_no);

    void update(StoryVO storyVO);

    List<StoryVO> selectList(StoryPageRequestDTO storypageRequestDTO);

    int selectCount(StoryPageRequestDTO storyPageRequestDTO);

    //이미지 처리
    @Insert("INSERT INTO story_file values(#{filename}, #{story_no}")
    void insertFile(StoryFileVO storyFileVO);


}
