package Graduation.CardVisor.config.jwt;

import Graduation.CardVisor.domain.member.LoginRequestDto;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

// UsernamePasswordAuthenticationFilter: 로그인 시도 위해 입력된 데이터를 DB 정보와 비교 후 인증하고, JWT 토큰 생성해서 응답에 넣어줌
// 1. attemptAuthentication() 함수: 인증과정 수행 후 Authentication 객체 반환 (이 객체는 스프링 시큐리티의 세션공간인 ContextHolder 에 저장됨)
// 2. successfulAuthentication() 함수: 인증완료 된 데이터로 JWT 토큰 생성 후, response 의 바디에 토큰 넣어줌

@RequiredArgsConstructor
@Slf4j
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    // attemptAuthentication: 입력된 로그인 정보로 인증 시도 후 Authentication(User) 객체 반환 (SecurityContextHolder 공간에 저장됨)
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        System.out.println("JwtAuthenticationFilter: Login is in Process");

        // 로그인을 위해 request 에 저장된 데이터를 파싱해서 loginRequestDto 객체에 저장
        ObjectMapper om = new ObjectMapper();
        LoginRequestDto loginRequestDto =null;
        try {
            loginRequestDto = om.readValue(request.getInputStream(), LoginRequestDto.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // UsernamePasswordAuthenticationToken: 인증을 위한 임시 토큰 (JWT 토큰 아님)
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(), loginRequestDto.getPassword());

        // 여기서 loadUserByUsername() 함수가 자동으로 실행됨 -> Authentication 객체 생성에 필요한 User 객체(=UserDetails) 얻어냄

        // Authentication(User) 형태로 인증 수행
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        return authentication;
    }

    // successfulAuthentication: 인증 성공 후 JWT 토큰 생성
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult)
            throws IOException, ServletException {

        System.out.println("successfulAuthentication 실행됨: 인증완료");

        // authResult 에서 User 데이터 꺼내오기
        User user =(User) authResult.getPrincipal();

        log.info(user.getUsername() + " " + user.getPassword() + " " + user.getAuthorities());

        // User 객체를 바탕으로 JWT 토큰 생성
        String jwtAccessToken = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION_TIME))
                .withIssuer(request.getRequestURL().toString())
                .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));
        String jwtRefreshToken = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 300 * 60 * 1000))
                .withIssuer(request.getRequestURL().toString())
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));

        // JWT 토큰 이름 설정
        Map<String, String> tokens = new HashMap<>();
        tokens.put("access_token", jwtAccessToken);
        tokens.put("refresh_token", jwtRefreshToken);

        // response 에 JWT 토큰 넣어줌
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), tokens);
    }
}
