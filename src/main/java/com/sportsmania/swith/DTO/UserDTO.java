package com.sportsmania.swith.DTO;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class userDTO {
    private String userId;
    private String name;
    private String pwd;
    private String nickname;
    private LocalDate birthday;
    private String phone;
    private String email;
    private String joinType;
    private String region;
    private String gender;
    private String image_profile;
    private String introduction;
    private String disabledType;
    private String grade;
    private String preference;
}
