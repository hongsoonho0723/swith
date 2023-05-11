package com.sportsmania.swith.config;


import com.sportsmania.swith.auth.PrinclpalDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;


@Configuration
@EnableWebSecurity // 스프링 시큐리티 필터가 스프링 필터체인에 등록
@Log4j2
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {



    private final UserDetailsService userDetailsService;

    @Bean // 해당 메서드의 리턴되는 오브젝트를 IoC로 등록해줌
    public BCryptPasswordEncoder encodePwd(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.csrf().disable();
        http.authorizeRequests()
                //   .antMatchers("/members/main").hasRole("USER")
                .antMatchers("/stories/posts").hasRole("USER")
                .antMatchers("/stories/posts/**").hasRole("USER")
                .antMatchers("/match/posts").hasRole("USER")
                .antMatchers("/members/main").permitAll()
                .antMatchers("/teams/posts","/teams/modify","/teams/admin/**","/teams/total/**").hasRole("USER")
                .antMatchers("/members/signin","/members/signup").anonymous() // 권한이 없는 사용자만 접근 가능
                .anyRequest().permitAll() // 나머지 페이지에 대한 인증이 필요없음
                .and()
                .formLogin()
                .loginPage("/members/signin")
                .loginProcessingUrl("/login") // /login 주소가 호출되면 시큐ㅜ리티가 낚아채서 대신진행해줌 / 겟매핑 x
                .defaultSuccessUrl("/members/main") // 로그인 성공시 리턴 url
                .and()
                .rememberMe() // Remember Me 기능 활성화
                .key("my-remember-me-key") // Remember Me 토큰 생성에 사용될 키
                .rememberMeParameter("remember-me") // 클라이언트에서 전달되는 Remember Me 파라미터 이름
                .userDetailsService(userDetailsService) // 사용자 정보 조회를 위한 UserDetailsService
                .rememberMeServices(tokenBasedRememberMeServices()) // 커스터마이징된 Remember Me 서비스 사용
                .and()
                .logout() // 로그아웃 처리
                .logoutUrl("/logout") // 로그아웃 URL 지정
                .logoutSuccessUrl("/members/main") // 로그아웃 성공시 이동할 URL 지정
                .invalidateHttpSession(true) // 세션 무효화
                .deleteCookies("JSESSIONID"); // 쿠키 삭제

    }

    @Bean
    public TokenBasedRememberMeServices tokenBasedRememberMeServices() {
        TokenBasedRememberMeServices rememberMeServices = new TokenBasedRememberMeServices("my-remember-me-key", userDetailsService);
        rememberMeServices.setTokenValiditySeconds(60 * 60 * 24 * 7); // 토큰 유효기간 7일로 설정
        return rememberMeServices;
    }

    @Bean
    public HttpFirewall allowUrlEncodedSlashHttpFirewall() {
        StrictHttpFirewall firewall = new StrictHttpFirewall();
        firewall.setAllowUrlEncodedDoubleSlash(true);
        firewall.setAllowSemicolon(true);
        return firewall;
    }

    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager("verificationCodes");
    }
}

