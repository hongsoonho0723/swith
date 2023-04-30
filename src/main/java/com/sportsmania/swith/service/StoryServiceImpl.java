package com.sportsmania.swith.service;

import com.sportsmania.swith.dto.PageRequestDTO;
import com.sportsmania.swith.dto.PageResponseDTO;
import com.sportsmania.swith.dto.StoryDTO;
import com.sportsmania.swith.mapper.StoryMapper;
import com.sportsmania.swith.domain.StoryVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class StoryServiceImpl implements StoryService {

    private final StoryMapper storyMapper;

    private final ModelMapper modelMapper;

    @Override
    public void register(StoryDTO storyDTO) throws IOException {

        log.info("register....service");

        //실제 저장 경로
        String uploadPath = "C:\\upload\\";

        //원래 파일 이름
        //MultipartFile multipartFile = storyDTO.getImage_main();

        String orginalImgName = storyDTO.getImage().getOriginalFilename(); //오리지널 이름은 필요 없음

        //확장자 추출
        String extension = orginalImgName.substring(orginalImgName.lastIndexOf("."));

        String saveName = UUID.randomUUID() + extension;

        //파일 불러올 때 사용할 파일 경로
        Path savePath = Paths.get(uploadPath, saveName);


        storyDTO.getImage().transferTo(savePath);

        log.info(modelMapper);

        StoryVO storyVO = StoryVO.builder()
                .story_no(storyDTO.getStory_no())
                .story_writer(storyDTO.getStory_writer())
                .s_category(storyDTO.getS_category())
                .title(storyDTO.getTitle())
                .content(storyDTO.getContent())
                .image_main(saveName) // 이미지 파일명 저장
                .date(storyDTO.getDate())
                .check(storyDTO.getCheck())
                .clicks(storyDTO.getClicks())
                .build();


        log.info(storyVO);

        storyMapper.insert(storyVO);

    }


    @Override
    public void remove(Long story_no) {

        storyMapper.delete(story_no);
        log.info("delete" + story_no);

    }

    @Override
    public void like(Long story_no, String userid) {

        storyMapper.likeCount(story_no);
    }

    @Override
    public PageResponseDTO<StoryDTO> getList(PageRequestDTO pageRequestDTO) {


        List<StoryVO> voList = storyMapper.selectList(pageRequestDTO);
        List<StoryDTO> dtoList = voList.stream()
                .map(vo -> modelMapper.map(vo, StoryDTO.class))
                .collect(Collectors.toList());

        int total = storyMapper.getCount(pageRequestDTO);

        PageResponseDTO<StoryDTO> pageResponseDTO = PageResponseDTO.<StoryDTO>withAll()
                .dtoList(dtoList)
                .total(total)
                // 페이지 번호의 처리를 위한 데이터들
                .pageRequestDTO(pageRequestDTO)
                .build();
        return pageResponseDTO;
    }

    @Override
    public StoryDTO getOne(Long story_no) {
        StoryVO storyVO = storyMapper.selectOne(story_no);
        //StoryDTO storyDTO = modelMapper.map(storyVO, StoryDTO.class);
        StoryDTO storyDTO = StoryDTO.builder()
                .story_no(storyVO.getStory_no())
                .story_writer(storyVO.getStory_writer())
                .s_category(storyVO.getS_category())
                .title(storyVO.getTitle())
                .content(storyVO.getContent())
                .image_main(storyVO.getImage_main())
                .date(storyVO.getDate())
                .check(storyVO.getCheck())
                .clicks(storyVO.getClicks())
                .build();


        return storyDTO;
    }
    

}
