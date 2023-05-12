package com.sportsmania.swith.service;

import com.sportsmania.swith.dto.BoardDTO;
import com.sportsmania.swith.dto.PageRequestDTO;
import com.sportsmania.swith.dto.PageResponseDTO;
import com.sportsmania.swith.mapper.BoardMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class BoardServiceImpl implements BoardService{
    private final ModelMapper modelMapper;
    private final BoardMapper boardMapper;
    @Override
    public void register(BoardDTO boardDTO) {
        log.info(modelMapper);
        log.info(boardDTO);
        boardMapper.insert(boardDTO);

    }

    @Override
    public PageResponseDTO<BoardDTO> getList(PageRequestDTO pageRequestDTO) {

        List<BoardDTO> dtoList = boardMapper.selectList(pageRequestDTO);

        int total = boardMapper.getCount(pageRequestDTO);

        PageResponseDTO<BoardDTO> pageResponseDTO = PageResponseDTO.<BoardDTO>withAll()
                .dtoList(dtoList)
                .total(total)
                .pageRequestDTO(pageRequestDTO)
                .build();

        return pageResponseDTO;
    }

    @Override
    public List<BoardDTO> mainList(BoardDTO boardDTO) {
        List<BoardDTO> mainList = boardMapper.mainList(boardDTO).stream()
                .map(vo -> modelMapper.map(vo,BoardDTO.class))
                .collect(Collectors.toList());

        return mainList;
    }
}
