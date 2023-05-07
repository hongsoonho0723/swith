package com.sportsmania.swith.mapper;

import com.sportsmania.swith.dto.LikesDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LikesMapper {

    // 좋아요 추가
    void insertLike(LikesDTO likesDTO);

    // 좋아요 삭제
    void deleteLike(LikesDTO likesDTO);

    // 특정 게시물에 대한 좋아요 여부 체크
    boolean isLikedByUser(Long storyNo, String userId);

    // 특정 게시물에 대한 좋아요 수 조회
    Long countLikes(Long storyNo);
}
