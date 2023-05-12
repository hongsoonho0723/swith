package com.sportsmania.swith.service;

import com.sportsmania.swith.dto.LikesDTO;

public interface LikesService {

    void addLike(LikesDTO likesDTO);

    void removeLike(LikesDTO likesDTO);

    boolean isLikedByUser(Long story_no, String userId);

    Long countLikes(Long story_no);


}
