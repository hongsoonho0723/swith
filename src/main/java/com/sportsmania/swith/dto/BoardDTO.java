package com.sportsmania.swith.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardDTO {
    private String board_no;
    private String board_writer;
    @NotEmpty
    private String exercise;
    @NotEmpty
    private String startdate;
    @NotEmpty
    private String enddate;
    private String deadline;
    @NotEmpty
    private String region;
    @NotEmpty
    private String region2;
    @NotNull
    private int recruitsNum;
    @NotEmpty
    private String title;
    @NotEmpty
    private String content;
    @NotEmpty
    private String b_category;
    private boolean finished;
    private String sportsfacility;
    private String supporttype;
    private String facilityadd;
    private boolean is_wish;
}
