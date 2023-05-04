package com.sportsmania.swith.service;

import com.sportsmania.swith.domain.ReplyVO;
import com.sportsmania.swith.dto.ReplyDTO;
import com.sportsmania.swith.mapper.ReplyMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReplyServiceImpl implements ReplyService {

    private final ReplyMapper replyMapper;

    private final ModelMapper modelMapper;

    public ReplyServiceImpl(ReplyMapper replyMapper, ModelMapper modelMapper) {
        this.replyMapper = replyMapper;
        this.modelMapper = modelMapper;
    }

    @Override
    public void register(ReplyDTO replyDTO) {
        ReplyVO replyVO = modelMapper.map(replyDTO, ReplyVO.class);
        replyMapper.insert(replyVO);
    }

    @Override
    public List<ReplyDTO> getList(Long story_no) {
        List<ReplyDTO> replyDTOList = replyMapper.getReplyList(story_no).stream()
                .map(vo -> modelMapper.map(vo, ReplyDTO.class))
                .collect(Collectors.toList());
        return replyDTOList;
    }

    @Override
    public ReplyDTO getReplyOne(Long reply_no) {
        ReplyVO replyVO = replyMapper.getReplyOne(reply_no);
        ReplyDTO replyDTO = modelMapper.map(replyVO, ReplyDTO.class);

        return replyDTO;
    }

    @Override
    public void modify(ReplyDTO replyDTO) {
        ReplyVO replyVO = modelMapper.map(replyDTO, ReplyVO.class);
        replyMapper.update(replyVO);
    }

    @Override
    public void remove(Long reply_no) {
        replyMapper.delete(reply_no);
    }
}
