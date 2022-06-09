package peermarket.peershop.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import peermarket.peershop.security.LoginSuccessHandler;

@Configuration
@EnableWebSecurity // 스프링 시큐리티 필터가 스프링 필터체인에 등록됨
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true) // secured 어노테이션 활성화, preAuthorize 어노테이션 활성화
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final LoginSuccessHandler loginSuccessHandler;

    //해당 메서드의 리턴되는 오브젝트를 ioc로 등록해줌.
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
//            .antMatchers("/user/**").authenticated()
            .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
            .anyRequest().permitAll()
        .and()
            .formLogin()
            .loginPage("/member/loginForm")
            .usernameParameter("email")
            .loginProcessingUrl(
                "/member/login") // /login 주소가 호출이 되면 시큐리티가 낚아채서 대신 로그인을 진행해줌. -> Controller에 로그인을 안만들어됨.
            .defaultSuccessUrl("/")
            .successHandler(loginSuccessHandler)
        .and()
            .sessionManagement()
            .maximumSessions(1)
            .expiredUrl("/member/loginForm");

    }

}
