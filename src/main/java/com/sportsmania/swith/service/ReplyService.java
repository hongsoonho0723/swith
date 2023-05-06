package com.sportsmania.swith.service;

import com.sportsmania.swith.domain.ReplyVO;
import com.sportsmania.swith.dto.ReplyDTO;

import java.util.List;

public interface ReplyService {

    void register(ReplyDTO replyDTO);

    List<ReplyDTO> getList(Long story_no);

    ReplyDTO getReplyOne(Long reply_no);

    void modify(ReplyDTO replyDTO);

    void remove(Long reply_no);

    int storyReplyCount(Long story_no);


}
