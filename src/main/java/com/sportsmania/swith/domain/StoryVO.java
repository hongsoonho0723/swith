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
    private LocalDateTime date;
    private int clicks;

    private int likeCount;

    private int replyCount;

    private double score;


}
