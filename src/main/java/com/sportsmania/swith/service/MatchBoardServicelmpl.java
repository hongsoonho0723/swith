package com.sportsmania.swith.service;

import com.sportsmania.swith.domain.MatchBoardVO;
import com.sportsmania.swith.dto.MatchBoardDTO;
import com.sportsmania.swith.dto.PageRequestDTO;
import com.sportsmania.swith.dto.PageResponseDTO;
import com.sportsmania.swith.mapper.MatchBoardMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Service
@Log4j2
@RequiredArgsConstructor

public class MatchBoardServicelmpl implements MatchBoardService {


    private final ModelMapper modelMapper;
    private final MatchBoardMapper matchBoardMapper;



 /*   @Override
    public List<MatchBoardDTO> getAll() {
        List<MatchBoardDTO> dtoList = MatchBoardMapper.selectAll().stream()
                .map(vo -> modelMapper.map(vo, MatchBoardDTO.class))
                .collect(Collectors.toList());
        return dtoList;
    }
*/



    @Override
    public void register(MatchBoardDTO matchBoardDTO) {
        log.info(modelMapper);


        MatchBoardVO matchBoardVO= modelMapper.map(matchBoardDTO,MatchBoardVO.class);
        log.info(matchBoardVO);
        matchBoardMapper.insert(matchBoardVO);

    }

    @Override
    public void modify(MatchBoardDTO matchBoardDTO) {
        MatchBoardVO matchBoardVO = modelMapper.map(matchBoardDTO, MatchBoardVO.class);
        matchBoardMapper.update(matchBoardVO);

    }

    @Override
    public MatchBoardDTO getOne(int board_no) {
        MatchBoardVO matchBoardVO = matchBoardMapper.selectOne(board_no);
        MatchBoardDTO matchBoardDTO = modelMapper.map(matchBoardVO, MatchBoardDTO.class);
        return matchBoardDTO;
    }

    @Override
    public void remove(int board_no) {
        matchBoardMapper.delete(board_no);
    }

    @Override
    public PageResponseDTO<MatchBoardDTO> getList(PageRequestDTO pageRequestDTO) {

        List<MatchBoardDTO> dtoList = matchBoardMapper.selectList(pageRequestDTO);

        int total = matchBoardMapper.getCount(pageRequestDTO);

        PageResponseDTO<MatchBoardDTO> pageResponseDTO = PageResponseDTO.<MatchBoardDTO>withAll()
                .dtoList(dtoList)
                .total(total)
                .pageRequestDTO(pageRequestDTO)
                .build();

        return pageResponseDTO;
    }


}
