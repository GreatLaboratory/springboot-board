package com.greatlaboratory.board.config.auth;

import com.greatlaboratory.board.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

// spring security를 쓰려면 꼭 있어야하는 설정 class
// @EnableWebSecurity 어노테이션을 꼭 써야하며, WebSecurityConfigurerAdapter을 상속받아서 configure메소드를 꼭 오버라이드해야한다.
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // h2-console 화면을 사용하기 위한 비활성화 옵션
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                    // url별 권한 관리를 설정하는 옵션의 시작점이 authorizeRequests
                    .authorizeRequests()
                    .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**").permitAll() // 해당 url엔 인증되지 않은 사용자 포함해서 전체에게 허용
                    .antMatchers("/api/v1/**").hasRole(Role.GUEST.name()) // GUEST의 ROLE을 가지고 있는 인증된 사용자만 해당 url 접근 허용
                    .anyRequest().authenticated() // 나머지 URL들 전부는 모두 인증된 사용자에게만 허용 (즉, 로그인된 사용자)
                .and()
                    // spring security에선 디폴트로 /logout이 로그아웃 url이다.
                    // 개발자가 별도로 /logout을 컨트롤러에 올리지 않아도 된다.
                    // 아래의 설정은 사용자가 /logout url을 요청하여 로그아웃이 성공했을 경우 / 주소로 이동하라는 뜻이다.
                    .logout().logoutSuccessUrl("/")
                .and()
                    // Oauth2 로그인 기능에 대한 설정의 진입점이 oauth2Login
                    .oauth2Login()
                        // Oauth2 로그인 성공 이후 사용자 정보를 가져올 설정 담당이 userInfoEndpoint
                        .userInfoEndpoint()
                            .userService(customOAuth2UserService);
    }
}
