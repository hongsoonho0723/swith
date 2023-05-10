package com.sportsmania.swith.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StoryDTO {


    private Long story_no;

    @NotEmpty
    private String story_writer;

    @NotEmpty
    private String s_category;

    @NotEmpty
    private String user_type;

    @NotEmpty
    private String title;

    @NotEmpty
    private String content;

    private String image_main;

    private LocalDateTime date;

    private int clicks;

    private int likeCount;

    private int replyCount;

    private double score;

    private String team_title;
    private String team_memberId;
    private boolean team_fixed;



}
