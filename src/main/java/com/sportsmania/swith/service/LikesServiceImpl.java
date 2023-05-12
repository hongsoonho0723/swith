package com.sportsmania.swith.service;

import com.sportsmania.swith.dto.LikesDTO;
import com.sportsmania.swith.mapper.LikesMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikesServiceImpl implements LikesService {

    private final LikesMapper likesMapper;

    @Override
    public void addLike(LikesDTO likesDTO) {
        likesMapper.insertLike(likesDTO);
    }

    @Override
    public void removeLike(LikesDTO likesDTO) {
        likesMapper.deleteLike(likesDTO);
    }

    @Override
    public boolean isLikedByUser(Long story_no, String userId) {
       return likesMapper.isLikedByUser(story_no, userId);
    }

    @Override
    public Long countLikes(Long story_no) {
        return likesMapper.countLikes(story_no);
    }
}
