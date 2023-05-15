package com.sportsmania.swith.controller;

import com.sportsmania.swith.domain.SupportTeamVO;
import com.sportsmania.swith.domain.UserVO;
import com.sportsmania.swith.dto.*;
import com.sportsmania.swith.mapper.UserMapper;
import com.sportsmania.swith.service.ChatService;
import com.sportsmania.swith.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/info")
@Log4j2
public class MemberController {


    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ChatService chatService;

    @GetMapping("/mypage")
    public void myPage(Model model,HttpSession httpSession){
        UserDTO userDTO = (UserDTO) httpSession.getAttribute("user");
        List<WishDTO> list = userService.wish(userDTO.getUserId());
        UserVO userVO = userMapper.findByUserId(userDTO.getUserId());
        List<ChatroomsDTO> chatroomsList = chatService.getChatrooms(userDTO.getNickname());
        List<BoardDTO> activeList = userService.activeList(userDTO.getNickname());

        List<BoardDTO> dtoList1 = new ArrayList<>();
        for (BoardDTO item: activeList) {
            if (item.isFinished()){
                item.setCheck("모집완료");
            }else{
                item.setCheck("모집중");
            }
        }

        List<WishDTO> dtoList = new ArrayList<>();
        for (WishDTO item : list) {
            WishDTO dto = new WishDTO();
            dto.setTitle(item.getTitle());
            dto.setB_category(item.getB_category());
            dto.setBoard_no(item.getBoard_no());
            dtoList.add(dto);
        }
        log.info(activeList);
        log.info(dtoList);

        model.addAttribute("activeList",activeList);
        model.addAttribute("wishlist", dtoList);
        model.addAttribute("userdto",userVO);
        model.addAttribute("chatroomsList",chatroomsList);
    }

    @GetMapping("/my")
    public String modify(HttpSession httpSession){
        return "info/mypageModify";
    }

    @PostMapping("/my")
    public String modify1(HttpSession httpSession, @RequestParam("file") MultipartFile file, @ModelAttribute UserDTO userDTO, Model model) throws IOException {

        String uploadPath = "C:\\upload\\";

        String originalFileName = file.getOriginalFilename();

        String filename = UUID.randomUUID() + originalFileName;

        log.info(filename);

        byte[] bytes = file.getBytes();
        Path path = Paths.get(uploadPath + filename);
        Files.write(path, bytes);


        log.info(userDTO);

        log.info("==============");

        UserDTO dto = (UserDTO) httpSession.getAttribute("user");

        log.info(dto);

        dto.setImage_profile(filename);
        dto.setPreference(userDTO.getPreference());
        dto.setIntroduction(userDTO.getIntroduction());
        httpSession.setAttribute("user",dto);
        userService.modify(dto);
        return "info/mypage";



       // String uploadPath = "C:\\upload\\"; //프로젝트 내부 저장
       /* String UPLOAD_DIR = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\assets\\user_image\\";

        String originalFileName = file.getOriginalFilename();

        String filename = UUID.randomUUID() + originalFileName; //동일한 파일명을 가진 파일이 저장될 경우 에러가 발생할 수 있기 때문에 파일명을 변경해여 저장

        byte[] bytes = file.getBytes();
        Path path = Paths.get(UPLOAD_DIR + filename);
        Files.write(path, bytes);


        //userDTO.setImage_profile(path.toString());
        String savePath = "../assets/user_image/" + filename;
        log.info(userDTO);

        log.info("==============");

        UserDTO dto = (UserDTO) httpSession.getAttribute("user");

        log.info(dto);

        dto.setImage_profile(savePath);
        dto.setPreference(userDTO.getPreference());
        dto.setIntroduction(userDTO.getIntroduction());
        httpSession.setAttribute("user",dto);
        userService.modify(dto);
        return "/info/mypage";*/


    }

    @GetMapping("/history")
    public void history(HttpSession httpSession){
        UserDTO dto = (UserDTO) httpSession.getAttribute("user");
        log.info("============");
        log.info(dto);
    }

    @GetMapping("/other/{nickname}")
    public String other(@PathVariable("nickname") String nickname,Model model){
            UserVO userVO = userMapper.findByNickname(nickname);
            model.addAttribute("other",userVO);
            return "info/other";
    }

}
