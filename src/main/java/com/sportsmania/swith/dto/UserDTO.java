package com.sportsmania.swith.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String userId;
    private String name;
    private String pwd;
    private String nickname;
    private String birthday;
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
    private String auth;
}
