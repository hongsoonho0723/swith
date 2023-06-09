package com.sportsmania.swith.controller;

import com.sportsmania.swith.domain.TeamMemberVO;
import com.sportsmania.swith.dto.StoryDTO;
import com.sportsmania.swith.dto.SupportTeamDTO;
import com.sportsmania.swith.dto.TeamMemberDTO;
import com.sportsmania.swith.dto.UserDTO;
import com.sportsmania.swith.service.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;


import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
@Log4j2
@RequiredArgsConstructor
public class SupportTeamController {

    private final SupportTeamService supportTeamService;
    private final TeamMemberService teamMemberService;
    private final StoryService storyService;
    private final UserService userService;
    private final ChatService chatService;

    @GetMapping("/members/teams/region")
    public ResponseEntity viewMainWithTeams(Authentication authentication){
        /*try{
            String userId = authentication.getName();
            List<SupportTeamDTO> teamList = supportTeamService.getRegion(userId);
            log.info("viewMainWithTeams:"+teamList);
            return new ResponseEntity<>(teamList, HttpStatus.OK);
        }catch(NullPointerException e){
            List<SupportTeamDTO> teamList = supportTeamService.getDeadline();
            log.info("viewMainWithTeams:"+teamList);
            return new ResponseEntity<>(teamList, HttpStatus.OK);
        }*/
        List<SupportTeamDTO> teamList = supportTeamService.getDeadline();
        for (SupportTeamDTO dto:
             teamList) {
            dto.setChatMember(chatService.getMembers(dto.getTeam_title()));
        }
        log.info("viewMainWithTeams:"+teamList);
        return new ResponseEntity<>(teamList, HttpStatus.OK);
    }

    @GetMapping("/teams/posts")
    public ModelAndView viewResgister() {
        ModelAndView mv = new ModelAndView("teams/sp-register");

        return mv;
    }

    @GetMapping("/teams/demo2")
    public ModelAndView viewDemo2() {
        List<SupportTeamDTO> dtoList = supportTeamService.getAll();
        ModelAndView mv = new ModelAndView("teams/sp-list2");
        mv.addObject("dtoList",dtoList);
        return mv;
    }
    @GetMapping("/teams/demo")
    public ResponseEntity viewDemo() {
        List<SupportTeamDTO> dtoList = supportTeamService.getAll();
        log.info("list demo 진입완료");
        return new ResponseEntity<>(dtoList,HttpStatus.OK);
    }

    /*@PostMapping("/teams/posts")
    public ResponseEntity registerPosts(@RequestBody SupportTeamDTO supportTeamDTO) {
        log.info("dto = " + supportTeamDTO);
        String deadline = supportTeamDTO.getDeadline();
        supportTeamDTO.setDeadline(deadline.replaceAll("T", " "));
        log.info("dto.deadline" + supportTeamDTO.getDeadline());

        supportTeamDTO.setTeam_writer("testUser1");
        supportTeamDTO.setImage_team("imagefileTest1");
        supportTeamService.register(supportTeamDTO);
        return new ResponseEntity<>(supportTeamDTO, HttpStatus.OK);
    }*/

   /* @PostMapping(value = "/teams/posts", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity registerImage(@RequestPart("image") MultipartFile image,
                                        @RequestPart("supportTeamDTO") SupportTeamDTO supportTeamDTO) throws Exception{
        supportTeamService.writeImage(supportTeamDTO, image);

        return new ResponseEntity(HttpStatus.OK);
    }
*/
    /*@PostMapping("/teams/posts")
    public ResponseEntity registerPosts(@ModelAttribute SupportTeamDTO supportTeamDTO,
                                        @RequestParam("file") MultipartFile file) throws IOException{

        log.info("등록전: " + supportTeamDTO);
        supportTeamDTO.setTeam_writer("testUser2");
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        supportTeamDTO.setImage_team(fileName);

        String uploadDir = "/assets/image_team/" + supportTeamDTO.getTeam_title();

        FileUploadUtil.saveFile(uploadDir, fileName, file);

        supportTeamService.register(supportTeamDTO);
        log.info("등록후: " + supportTeamDTO);
        return new ResponseEntity<>(supportTeamDTO,HttpStatus.OK);
    }*/

    //private static String UPLOAD_DIR = "/assets/image_team/";  // 업로드할 디렉토리를 지정합니다.
    /* String UPLOAD_DIR = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\assets\\image_team";*/
    @PostMapping("/teams/posts")
    public ResponseEntity uploadFIle(@RequestParam("image") MultipartFile file,
                                     @ModelAttribute SupportTeamDTO supportTeamDTO,
                                     Authentication authentication) throws IOException {
        String team_writer = authentication.getName();
        supportTeamDTO.setTeam_writer(team_writer);
        if (file.isEmpty()) {
            log.info("file empty");
            return new ResponseEntity<>("Please select a file!", HttpStatus.OK);
        }

        try {
            log.info("드디어 받아온" + supportTeamDTO);
            supportTeamService.registerWithFile(supportTeamDTO, file);


        } catch (Exception e) {

            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }


        //return new ResponseEntity<>("Successfully uploaded - " + supportTeamDTO, HttpStatus.OK);

        //supportTeamService.registerWithFile(supportTeamDTO, file);


        return new ResponseEntity<>("Successfully uploaded - " + supportTeamDTO, HttpStatus.OK);
    }

    /*// 파일을 디스크에 저장합니다.
    private void saveUploadedFile(MultipartFile file) throws Exception {
        if (!file.isEmpty()) {
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOAD_DIR + file.getOriginalFilename());
            Files.write(path, bytes);

            SupportTeamDTO supportTeamDTO = supportTeamService.getOne("testTeam1");
            log.info("path.toString() 전 : " + supportTeamDTO);
            supportTeamDTO.setImage_team(path.toString());
            log.info("path.toString() 후 : " + supportTeamDTO);
            supportTeamService.modify(supportTeamDTO);
        }
    }*/

    @GetMapping("/teams")
    public ModelAndView viewList() {
        List<SupportTeamDTO> dtoList = supportTeamService.getAll();

        for (SupportTeamDTO dto: dtoList) {
            UserDTO userDTO = userService.findByUsername(dto.getTeam_writer());
            dto.setNickname(userDTO.getNickname());
            dto.setChatMember(chatService.getMembers(dto.getTeam_title()));
            log.info("입장멤버수:"+dto.getChatMember()+", 팀제목:"+dto.getTeam_title());
        }

        ModelAndView mv = new ModelAndView("teams/sp-list");
        log.info("teams view Controller 작동완료");
        mv.addObject("dtoList", dtoList);
        return mv;
    }

    @GetMapping("/teams/search/{category}/{keyword}")
    public ResponseEntity viewSearchList(@PathVariable("category") String category,
                                         @PathVariable("keyword") String keyword){
        log.info("검색컨트롤ㄹ러들어옴");
        log.info("category: " + category);
        Boolean finished = null;

        if(category.equals("1")){
            finished = true;
        } else if (category.equals("0")) {
            finished = false;
        }
        log.info("finished: " + finished + ",keyword: " + keyword);

        List<SupportTeamDTO> dtoList = supportTeamService.getSearch(finished, keyword);
        for (SupportTeamDTO dto :
                dtoList) {
            UserDTO userDTO = userService.findByUsername(dto.getTeam_writer());
            dto.setNickname(userDTO.getNickname());
            dto.setChatMember(chatService.getMembers(dto.getTeam_title()));
        }
        log.info("검색dtoList:"+dtoList);
        return new ResponseEntity<>(dtoList,HttpStatus.OK);
    }

    /*@GetMapping("/teams")
    public ResponseEntity getList(@RequestParam("row_num") int row_num){
        //Integer num = Integer.parseInt(row_num);
        List<SupportTeamDTO> dtoList = supportTeamService.getPage(row_num);
        log.info("teams get list 출력");
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }*/


    @GetMapping("/teams/{team_title}")
    public ModelAndView viewPost(@PathVariable("team_title") String team_title) {
        SupportTeamDTO supportTeamDTO = supportTeamService.getOne(team_title);
        String team_writer = supportTeamDTO.getTeam_writer();
        UserDTO userDTO = userService.findByUsername(team_writer);
        supportTeamDTO.setNickname(userDTO.getNickname());
        log.info("list view" + supportTeamDTO);
        ModelAndView mv = new ModelAndView("teams/sp-view");
        mv.addObject("dto", supportTeamDTO);
        return mv;
    }

    @GetMapping("/teams/{team_title}/{team_fixed}")  //teams/members/1
    public ResponseEntity transferViewData(@PathVariable("team_title") String team_title,
                                           @PathVariable("team_fixed") int team_fixed) {
        if (team_fixed == 1) {
            List<TeamMemberDTO> memberList = teamMemberService.getMember(team_title);
            for (TeamMemberDTO dto:
                    memberList) {
                UserDTO userDTO = userService.findByUsername(dto.getTeam_memberId());
                dto.setTeam_memberNickname(userDTO.getNickname());
                log.info(dto.getTeam_memberNickname());
            }

            log.info(memberList);
            return new ResponseEntity<>(memberList, HttpStatus.OK);
        } else {
            log.info("team_fixed: " + team_fixed);
            List<TeamMemberDTO> userList = teamMemberService.getUser(team_title);
            for (TeamMemberDTO dto:
                    userList) {
                UserDTO userDTO = userService.findByUsername(dto.getTeam_memberId());
                dto.setTeam_memberNickname(userDTO.getNickname());
                log.info(dto.getTeam_memberNickname());
            }
            log.info(userList);
            return new ResponseEntity<>(userList, HttpStatus.OK);
        }
    }

    @DeleteMapping("/teams/admin/{team_title}/{team_memberId}")
    public ResponseEntity rejectMember(@PathVariable("team_title") String team_title,
                                       @PathVariable("team_memberId") String team_memberId) {
        TeamMemberDTO teamMemberDTO = TeamMemberDTO.builder()
                .team_title(team_title)
                .team_memberId(team_memberId)
                .team_fixed(false)
                .build();
        teamMemberService.removeMember(teamMemberDTO);
        log.info("Controller : 멤버 요청 거절 service 실행");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/teams/admin/{team_title}/{team_memberId}")
    public ResponseEntity acceptMember(@PathVariable("team_title") String team_title,
                                       @PathVariable("team_memberId") String team_memberId) {
        UserDTO userDTO =userService.findByUsername(team_memberId);
        TeamMemberDTO teamMemberDTO = TeamMemberDTO.builder()
                .team_title(team_title)
                .team_memberId(team_memberId)
                .team_fixed(true)
                .team_memberNickname(userDTO.getNickname())
                .build();
        teamMemberService.modify(teamMemberDTO);
        log.info("Controller : 멤버 요청 수락 service 실행");
        return new ResponseEntity<>(teamMemberDTO, HttpStatus.OK);
    }

    @PostMapping("/teams/info")
    public ResponseEntity applicationTeam(@RequestBody TeamMemberDTO teamMemberDTO,
                                          Authentication authentication) {
        String userId = authentication.getName();
        teamMemberDTO.setTeam_memberId(userId);
        teamMemberDTO.setTeam_fixed(false);
        log.info("applicationTeam()의 dto: " + teamMemberDTO);
        teamMemberService.register(teamMemberDTO);
        return new ResponseEntity<>(teamMemberDTO, HttpStatus.OK);
    }

    @DeleteMapping("/teams/admin/{team_title}")
    public ResponseEntity removeTeam(@RequestBody SupportTeamDTO supportTeamDTO,
                                     Authentication authentication) {

        String team_title = supportTeamDTO.getTeam_title();
        String nickname = supportTeamDTO.getTeam_writer();
        log.info("nickname:"+nickname);
        UserDTO userDTO = userService.findByUserNickname(nickname);
        supportTeamDTO.setTeam_writer(userDTO.getUserId());
        List<TeamMemberDTO> teamList = teamMemberService.getAll(team_title);
        if (!teamList.isEmpty()) {
            IntStream.rangeClosed(0, teamList.size() - 1).forEach(i -> {
                TeamMemberDTO teamMemberDTO = teamList.get(i);
                teamMemberService.removeMember(teamMemberDTO);
            });
        }
        log.info("removeTeam()의 dto: " + supportTeamDTO);

        if(authentication.getName().equals(supportTeamDTO.getTeam_writer())){
            supportTeamService.remove(team_title);
        }else{
            log.info("작성자와 로그인한 유저가 달라서 팀삭제 실패");
        }

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/teams/total/{team_title}")
    public ResponseEntity recruitFinished(@RequestBody SupportTeamDTO supportTeamDTO) {
        SupportTeamDTO supportTeamDTO1 = supportTeamService.getOne(supportTeamDTO.getTeam_title());
        log.info("isFinished: " + supportTeamDTO.isFinished());
        if (supportTeamDTO.getInquiry() == null) {
            supportTeamDTO1.setFinished(supportTeamDTO.isFinished());
        }
        supportTeamService.modify(supportTeamDTO1);
        return new ResponseEntity<>(supportTeamDTO1, HttpStatus.OK);
    }

   /* @GetMapping("/teams/admin/{team_title}")
    public ResponseEntity viewModifyPage(@PathVariable("team_title") String team_title) {
        SupportTeamDTO supportTeamDTO = supportTeamService.getOne(team_title);
        ResponseEntity responseEntity;
        //if(userId.equals(supportTeamDTO.getTeam_writer)){
        responseEntity = new ResponseEntity(HttpStatus.OK);
        //}
        return responseEntity;
    }*/


    @GetMapping("/admin/{team_title}")
    public ModelAndView viewModify(@PathVariable("team_title") String team_title) {
        SupportTeamDTO supportTeamDTO = supportTeamService.getOne(team_title);
        log.info("modify view" + supportTeamDTO);
        ModelAndView mv = new ModelAndView("teams/sp-modify");
        mv.addObject("dto", supportTeamDTO);
        return mv;
    }

    @PutMapping("/teams/admin/{team_title}")
    public ResponseEntity modifyTeam(@RequestBody SupportTeamDTO supportTeamDTO,
                                     @PathVariable("team_title") String team_title,
                                     Authentication authentication) {
        String team_writer = authentication.getName();
        supportTeamDTO.setTeam_writer(team_writer);
        String deadline = supportTeamDTO.getDeadline();
        supportTeamDTO.setDeadline(deadline.replaceAll("T", " "));
        supportTeamDTO.setTeam_title(team_title);
        log.info("modify dto: " + supportTeamDTO);

        supportTeamService.modify(supportTeamDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }



    @GetMapping("/teams/members")
    public ResponseEntity<Boolean> getAllMember(Authentication authentication) {
        List<TeamMemberDTO> memberList = teamMemberService.getAllMember();
        String currentUserId = authentication.getName();
        boolean isMember = memberList.stream()
                .anyMatch(member -> member.getTeam_memberId().equals(currentUserId));
        log.info("isMember: {}", isMember);
        return new ResponseEntity<>(isMember, HttpStatus.OK);
    }

   /* @GetMapping("teams/stories/{team_title}")
    public ResponseEntity<List<StoryDTO>> getTeamStory(@PathVariable("team_title") String team_title){

        return new ResponseEntity<>();
    }*/

    @GetMapping("teams/list")
    public ResponseEntity<List> getUserTeams( Authentication authentication) {
        List<StoryDTO> teamList = teamMemberService.getUserTeams(authentication.getName());
        log.info(teamList);
        return new ResponseEntity<>(teamList,HttpStatus.OK);
    }

    @GetMapping("/teams/{team_title}/stories")
    public ResponseEntity<List> getTeamStories(@PathVariable("team_title") String team_title) {
        List<StoryDTO> teamStoryList = teamMemberService.getTeamStories(team_title);
        log.info(teamStoryList);
        return new ResponseEntity<>(teamStoryList, HttpStatus.OK);
    }


}