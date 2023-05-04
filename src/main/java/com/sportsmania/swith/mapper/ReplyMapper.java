package com.sportsmania.swith.mapper;

import com.sportsmania.swith.domain.ReplyVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReplyMapper {

    void insert (ReplyVO replyVO);

    ReplyVO getReplyOne(Long reply_no);

    void delete(Long reply_no);

    void  replyCount(Long story_no);

    void update(ReplyVO replyVO);

    List<ReplyVO> getReplyList(Long story_no);

   // int getCount(PageRequestDTO pageRequestDTO);


}
