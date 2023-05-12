package com.sportsmania.swith.domain;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StoryVO {

    private Long story_no;
    private String story_writer;
    private String user_type;
    private String s_category;
    private String title;
    private String content;
    private String image_main;
    private String date;
    private int clicks;

    private int likeCount;

    private int replyCount;

    private double score;

    private Long team_no;
    private String team_title;
    private String team_memberId;
    private boolean team_fixed;
    private String image_profile;
    private String nickname;



}
