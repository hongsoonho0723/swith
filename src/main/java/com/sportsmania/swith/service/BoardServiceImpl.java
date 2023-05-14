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

        List<BoardDTO> list = dtoList;

        for (BoardDTO dto:list) {
            String roomtitle = dto.getBoard_no()+dto.getB_category()+dto.getNickname();
            int recruitsNumadd = dto.getRecruitsNum()+1;
            dto.setRecruitsNum(recruitsNumadd);
            int num =boardMapper.countAdditionalNum(roomtitle);
            dto.setAdditionalNum(num);
//            log.info("지원인원수"+dto.getAdditionalNum());
//            log.info("모집인원수+1 : "+dto.getRecruitsNum());
//            log.info(dto.getExercise());
            if(dto.getAdditionalNum()==dto.getRecruitsNum()){
                dto.setFinished(true);
            } else if (dto.getAdditionalNum()!=dto.getRecruitsNum()){
                dto.setFinished(false);
            }
        }
        dtoList = list;

        int total = boardMapper.getCount(pageRequestDTO);
//        int addUserNum = boardMapper.countAdditionalNum();
        PageResponseDTO<BoardDTO> pageResponseDTO = PageResponseDTO.<BoardDTO>withAll()
                .dtoList(dtoList)
                .total(total)
//                .addUserNum(addUserNum)
                .pageRequestDTO(pageRequestDTO)
                .build();

        return pageResponseDTO;
    }

    @Override
    public List<BoardDTO> mainList(BoardDTO boardDTO) {
        List<BoardDTO> mainList = boardMapper.mainList(boardDTO).stream()
                .map(vo -> modelMapper.map(vo,BoardDTO.class))
                .collect(Collectors.toList());

        List<BoardDTO> list = mainList;

        for (BoardDTO dto:list) {
            String roomtitle = dto.getBoard_no()+dto.getB_category()+dto.getNickname();
            int recruitsNumadd = dto.getRecruitsNum()+1;
            dto.setRecruitsNum(recruitsNumadd);
            int num =boardMapper.countAdditionalNum(roomtitle);
            dto.setAdditionalNum(num);
//            log.info("지원인원수"+dto.getAdditionalNum());
//            log.info("모집인원수+1 : "+dto.getRecruitsNum());
        }
        mainList = list;

        return mainList;
    }

    @Override
    public int getaddtionNum(String roomtitle) {
        int num = boardMapper.countAdditionalNum(roomtitle);
        return num;
    }
}
