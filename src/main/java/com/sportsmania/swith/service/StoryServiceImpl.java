package com.sportsmania.swith.service;

import com.sportsmania.swith.dto.StoryDTO;
import com.sportsmania.swith.dto.page.StoryPageRequestDTO;
import com.sportsmania.swith.dto.page.StoryPageResponseDTO;
import com.sportsmania.swith.mapper.StoryMapper;
import com.sportsmania.swith.domain.StoryVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
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
    public void register(StoryDTO storyDTO) {

        log.info("register....service");
        //실제 저장 경로
        String uploadPath = "C:\\upload\\";

       /* String originalImgName = storyDTO.getImage().getOriginalFilename();

        //확장자 추출
        String extension = originalImgName.substring(originalImgName.lastIndexOf("."));

        String saveName = UUID.randomUUID() + extension;

        //파일 불러올 때 사용할 파일 경로
        Path savePath = Paths.get(uploadPath, saveName);

        try {
            storyDTO.getImage().transferTo(savePath);
        } catch (IOException e) {
            e.printStackTrace();
        }

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

        storyMapper.insert(storyVO);*/

    }

    @Override
    public void registerWithFile(StoryDTO storyDTO, MultipartFile file) throws IOException {
        //String uploadPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\assets\\storyfile\\";

        String uploadPath = "C:\\upload\\";

        String originalFileName = file.getOriginalFilename();

        String filename = UUID.randomUUID() + originalFileName;

        byte[] bytes = file.getBytes();
        Path path = Paths.get(uploadPath + filename);
        Files.write(path, bytes);

        storyDTO.setImage_main(filename);

        StoryVO storyVO = modelMapper.map(storyDTO, StoryVO.class);

        storyMapper.insert(storyVO);
    }

    @Override
    public void increaseViewCount(Long storyNo) {

            storyMapper.increaseViewCount(storyNo);

    }



    @Override
    public void remove(Long story_no) {

        //storyMapper.deleteStoryWithReplies(story_no);
        storyMapper.delete(story_no);
        log.info("delete" + story_no);

    }

    @Override
    public void like(Long story_no, String userid) {

        storyMapper.likeCount(story_no);
    }

    @Override
    public void modify(StoryDTO storyDTO) {
        StoryVO storyVO=  modelMapper.map(storyDTO, StoryVO.class );
        storyMapper.update(storyVO);
    }



    @Override
    public StoryPageResponseDTO<StoryDTO> getList(StoryPageRequestDTO storyPageRequestDTO) {
        List<StoryVO> voList = storyMapper.selectList(storyPageRequestDTO);
        List<StoryDTO> dtoList = voList.stream()
                .map(vo -> modelMapper.map(vo, StoryDTO.class))
                .collect(Collectors.toList());

        int total = storyMapper.selectCount(storyPageRequestDTO);

        StoryPageResponseDTO<StoryDTO> storyPageResponseDTO =StoryPageResponseDTO.<StoryDTO>withAll()
                .dtoList(dtoList)
                .total(total)
                // 페이지 번호의 처리를 위한 데이터들
                .storyPageRequestDTO(storyPageRequestDTO)
                .build();
        return storyPageResponseDTO;
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
                .clicks(storyVO.getClicks())
                .build();


        return storyDTO;
    }

    @Override
    public List<StoryVO> getPopularStories() {
       List<StoryVO> popularStories = storyMapper.getPopularStories();
       return popularStories;
    }


}
