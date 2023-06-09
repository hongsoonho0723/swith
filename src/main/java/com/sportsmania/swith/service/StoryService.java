package com.sportsmania.swith.service;

import com.sportsmania.swith.domain.StoryVO;
import com.sportsmania.swith.dto.StoryDTO;
import com.sportsmania.swith.dto.page.StoryPageRequestDTO;
import com.sportsmania.swith.dto.page.StoryPageResponseDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface StoryService {


    void registerWithFile(StoryDTO storyDTO, MultipartFile file) throws  IOException;

    void increaseViewCount(Long storyNo);

    void remove(Long story_no);

    void like(Long story_no, String userid);

    void modify(StoryDTO storyDTO, MultipartFile file) throws IOException;

    StoryPageResponseDTO<StoryDTO> getList(StoryPageRequestDTO storyPageRequestDTO);

    StoryDTO getNextStory(Long story_no);

    StoryDTO getOne(Long story_no);

    List<StoryVO> getPopularStories();
}
