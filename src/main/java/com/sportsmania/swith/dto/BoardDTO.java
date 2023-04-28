package com.sportsmania.swith.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardDTO {
    @NotEmpty
    private String board_no;
    @NotEmpty
    private String board_writer;
    @NotEmpty
    private String exercise;
    @NotEmpty
    private LocalDateTime startdate;
    @NotEmpty
    private LocalDateTime enddate;
    @NotEmpty
    private String region;
    @NotEmpty
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
}
