package com.sportsmania.swith.auth;

// 시큐리티가 /login 낚아채서
// 로그인을 진행이 완료가 되면 시큐리티 세션을 만들어 준다.
// Authenication 안에 user정보가 있어야함 
// User오브젝트 타입 => UserDetails 타입 객체 

import com.sportsmania.swith.domain.UserVo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

// security session => Authenication => UserDetails 타입
public class PrinclpalDetails extends UserVo implements UserDetails {

    private UserVo userVo; // 컴포지션

    public PrinclpalDetails(UserVo userVo){
        this.userVo = userVo;
    }

    // 해당 유저의 권한을 리턴 userVo.getAuth
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return userVo.getAuth();
            }
        });
        return collection;
    }

    @Override
    public String getPassword() {
        return userVo.getPwd();
    }

    @Override
    public String getUsername() {
        return userVo.getUserId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        // 1년동안 로그인 안하면 휴먼 하겠다 !!
        return true;
    }
}
