package com.sportsmania.swith.service;

import com.sportsmania.swith.dto.BoardJjimDTO;
import com.sportsmania.swith.mapper.BoardJjimMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BoardJjimServiceImpl implements BoardJjimService{
    private final BoardJjimMapper boardJjimMapper;
    private final ModelMapper modelMapper;

    @Override
    public void register(BoardJjimDTO boardJjimDTO) {
        boardJjimMapper.insertJjim(boardJjimDTO);
    }

    @Override
    @Transactional
    public List<BoardJjimDTO> getJjimListByUserid(String userid) {
        //찜한 목록 찾지
        List<BoardJjimDTO> likeList = boardJjimMapper.getJjimListByUserid(userid);

        //찜한 목록이없으면 null반환
        if(Objects.isNull(likeList)){
            return null;
        }


        return likeList;
    }

    @Override
    public void remove(int board_no, String userid) {
        boardJjimMapper.deleteJjim(board_no,userid);

    }

    @Override
    public BoardJjimDTO getByBnoWithUserid(BoardJjimDTO boardJjimDTO) {
        return null;
    }
}
