package com.sportsmania.swith.mapper;

import com.sportsmania.swith.domain.ReplyVO;
import com.sportsmania.swith.domain.StoryVO;
import com.sportsmania.swith.dto.PageRequestDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReplyMapper {

    void insert (ReplyVO replyVO);

    void delete(Long reply_no);

    void  replyCount(Long story_no);

    void update(ReplyVO replyVO);

    List<ReplyVO> getReplyList(Long story_no);

   // int getCount(PageRequestDTO pageRequestDTO);


}
