package com.sportsmania.swith.domain;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class StoryVO {

    private Long story_no;
    private String story_writer;
    private String s_category;
    private String title;
    private String content;
    private String image_main;
    // String date;
    private LocalDateTime date;
    private int check; //좋아요
    private int clicks;


}
