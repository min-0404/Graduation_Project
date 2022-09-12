package Graduation.CardVisor.controller;


import Graduation.CardVisor.config.jwt.JwtProperties;
import Graduation.CardVisor.domain.member.Member;
import Graduation.CardVisor.service.MemberService;
import Graduation.CardVisor.service.RegisterService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;

@RestController
@Slf4j
@RequiredArgsConstructor
public class HomeController {

    private final RegisterService registerService;
    private final MemberService memberService;

    // 회원가입
    @PostMapping("/register")
    public ResponseEntity<String> join(@RequestBody Member member){

        if(registerService.saveMember(member) != null){
            return ResponseEntity.ok().body("Sign in Successful");
        }else{
            return ResponseEntity.badRequest().body("Username Exists");
        }
    }

    // 아이디 중복 검사
    @GetMapping("/duplicate")
    public ResponseEntity<String> checkDuplicate(@RequestHeader String nickname){
        return registerService.checkDuplicateNickname(nickname);
    }


    @GetMapping("/refreshToken")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String authorizationHeader = request.getHeader(AUTHORIZATION);

        if(authorizationHeader != null && authorizationHeader.startsWith(JwtProperties.TOKEN_PREFIX)) {
            try {
                String jwtRefreshToken = authorizationHeader.substring(JwtProperties.TOKEN_PREFIX.length());
                JWTVerifier verifier = JWT.require(Algorithm.HMAC512(JwtProperties.SECRET)).build();
                DecodedJWT decodedJWT = verifier.verify(jwtRefreshToken);
                String username = decodedJWT.getSubject();

                Member member = memberService.getMember(username);

                String jwtAccessToken = JWT.create()
                        .withSubject(member.getNickname())
                        .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION_TIME))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", member.getRolesList())
                        .sign(Algorithm.HMAC512(JwtProperties.SECRET));

                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", jwtAccessToken);
                tokens.put("refresh_token", jwtRefreshToken);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);

            } catch (Exception exception) {
                response.setHeader("error", exception.getMessage());
                response.setStatus(FORBIDDEN.value());
//                    response.sendError(FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("error_message", exception.getMessage());
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }

        } else {
            throw new RuntimeException("Refresh token is missing");
        }
    }



}
