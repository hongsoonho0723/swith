package com.sportsmania.swith.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SupportTeamDTO {
    private String team_title;
    private String team_writer;
    private String content;
    private String sido;
    private String sigungu;
    private int member_num;
    private String regdate;
    private String image_team;
    private String simple_content;
    private String inquiry;
    private boolean finished;
    private String deadline;

}
