package com.sportsmania.swith.service;

import com.sportsmania.swith.domain.MatchBoardVO;
import com.sportsmania.swith.domain.UserVO;
import com.sportsmania.swith.dto.MatchBoardDTO;
import com.sportsmania.swith.dto.UserDTO;
import com.sportsmania.swith.mapper.MatchBoardMapper;
import com.sportsmania.swith.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

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
    public void modify1(MatchBoardDTO matchBoardDTO) {
        MatchBoardVO matchBoardVO = modelMapper.map(matchBoardDTO, MatchBoardVO.class);
        matchBoardMapper.update1(matchBoardVO);

    }

    @Override
    public MatchBoardDTO getOne(int board_no) {
        MatchBoardVO matchBoardVO = matchBoardMapper.selectOne(board_no);
        MatchBoardDTO matchBoardDTO = modelMapper.map(matchBoardVO, MatchBoardDTO.class);
        return matchBoardDTO;
    }

    @Override
    public UserDTO getOne1(String userId) {
        UserVO userVO = matchBoardMapper.selectOne1(userId);
        UserDTO userDTO= modelMapper.map(userVO, UserDTO.class);


        return userDTO;
    }


    @Override
    public void remove(int board_no) {
        matchBoardMapper.delete(board_no);
    }

    @Override
    public void increaseViewCount(int board_no) {
        matchBoardMapper.increaseViewCount(board_no);
    }


}
