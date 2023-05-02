package com.sportsmania.swith.domain;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserVo{
    private String userId;
    private String name;
    private String pwd; // 시큐리티로 로그인 하려면 패스워드 암호화를 해야함
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
    private String auth;

}
