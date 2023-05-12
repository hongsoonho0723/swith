package com.sportsmania.swith.domain;

import lombok.*;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SupportTeamVO {
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
