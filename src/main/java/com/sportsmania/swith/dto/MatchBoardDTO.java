package com.sportsmania.swith.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@ToString
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MatchBoardDTO {



    private int board_no;
    @NotEmpty
    private String board_writer;
    @NotEmpty
    private String exercise;
    @NotEmpty
    private String startdate;
    @NotEmpty
    private String enddate;
    @NotEmpty
    private String region;
    private String region2;
    @NotNull
    private int recruitsNum;
    @NotEmpty
    private String title;
    @NotEmpty
    private String content;
    private String b_category;
    private boolean finished;

    @NotEmpty
    private String regdate;
    @NotNull
    private int clicks;
    private String sportsfacility;
    private String supporttype;
    private String facilityadd;
    private boolean is_wish;
    private String introduction;

}
