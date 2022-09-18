package Graduation.CardVisor.config;


import Graduation.CardVisor.config.jwt.AuthEntryPointJwt;
import Graduation.CardVisor.config.jwt.AuthTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CorsFilter corsFilter;
    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder passwordEncoder;

    private final AuthEntryPointJwt unauthorizedHandler;

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // csrf 비활성
        http.csrf().disable();

        http.cors();

        http.exceptionHandling().authenticationEntryPoint(unauthorizedHandler);

        // 세션 규칙 설정
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()

                // 필터 추가: addFilter 하면, 필터에 정의된 함수들이 자동으로 실행됨
                .addFilter(corsFilter)
                .addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class)

                // 페이지별 접근 권한 설정
                .authorizeRequests()
                .antMatchers("/auth/**").permitAll() // 회원가입, 로그인 메뉴, 중복검사는 누구나 접근 가능
                .antMatchers("/benefit/**", "/card/**", "/member/**")
                .access("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
                .antMatchers("/admin/**")
                .access("hasRole('ROLE_ADMIN')")
                .anyRequest().authenticated();
        ;
    }

    // 로그인 시 입력된 password 를 인코딩해서, 인증 수행?
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
