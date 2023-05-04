package com.sportsmania.swith.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TeamMemberVO {
    private String team_title;
    private String team_memberId;
    private boolean team_fixed;
}
