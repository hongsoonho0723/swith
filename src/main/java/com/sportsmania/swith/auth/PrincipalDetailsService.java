package com.sportsmania.swith.auth;

import com.sportsmania.swith.domain.UserVO;
import com.sportsmania.swith.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// 시큐리티 설정에서 login 요청이 오면 자동으로 UserDetailsService타입으로 Ioc되어있는 loadByUsername함수가 실행
@Service
public class PrincipalDetailsService implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserVO userVo = userMapper.findByUserId(username);
        if (userVo != null){
            return new PrinclpalDetails(userVo);
        }
        return null;
    }
}
