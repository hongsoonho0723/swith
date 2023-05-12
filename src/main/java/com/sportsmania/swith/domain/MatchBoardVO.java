package com.sportsmania.swith.domain;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class MatchBoardVO {

    private int board_no;
    private String board_writer;
    private String exercise;
    private String startdate;
    private String enddate;
    private String region;
    private String region2;
    private int recruitsNum;
    private String title;
    private String content;
    private String b_category;
    private int finished;

    private String regdate;
    private int clicks;
    private String sportsfacility;
    private String supporttype;
    private String facilityadd;
    private String introduction;
    private String nickname;
}
