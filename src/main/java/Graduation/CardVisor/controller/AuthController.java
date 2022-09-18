package Graduation.CardVisor.controller;


import Graduation.CardVisor.config.jwt.JwtUtils;
import Graduation.CardVisor.config.userDetails.UserDetailsImpl;
import Graduation.CardVisor.config.userDetails.UserDetailsServiceImpl;
import Graduation.CardVisor.domain.member.LoginRequestDto;
import Graduation.CardVisor.domain.member.Member;
import Graduation.CardVisor.service.RegisterService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final RegisterService registerService;

    private final AuthenticationManager authenticationManager;

    private final UserDetailsServiceImpl userDetailsService;

    private final JwtUtils jwtUtils;

    // 로그인
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequestDto loginRequestDto, HttpServletRequest request) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(), loginRequestDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        String accessToken = jwtUtils.generateAccessTokenFromMember(userDetails);

        ResponseCookie jwtCookie = jwtUtils.generateRefreshTokenCookie(userDetails, request.getRequestURL());

        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        Map<String, Object> store = new HashMap<>();

        store.put("access_token", accessToken);
        store.put("roles", roles);

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString()).body(store);
    }

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
        String refreshToken = parseJwt(request);
        if(refreshToken != null && jwtUtils.validateJwtToken(refreshToken)) {
            try {
                String username = jwtUtils.getUserNameFromJwtToken(refreshToken);
                UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername(username);

                String accessToken = jwtUtils.generateAccessTokenFromMember(userDetails);

                List<String> roles = userDetails.getAuthorities().stream()
                        .map(item -> item.getAuthority())
                        .collect(Collectors.toList());

                Map<String, Object> store = new HashMap<>();

                store.put("access_token", accessToken);
                store.put("roles", roles);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), store);
            } catch (Exception exception) {
                response.setHeader("error", exception.getMessage());
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);

                Map<String, String> error = new HashMap<>();
                error.put("error", exception.getMessage());
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        } else {
            response.setHeader("error", "refreshToken is not valid");
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);

            Map<String, String> error = new HashMap<>();
            error.put("error_message", "refreshToken is not valid");
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            new ObjectMapper().writeValue(response.getOutputStream(), error);
        }
    }

    @GetMapping("/signout")
    public ResponseEntity<?> logoutUser() {
        ResponseCookie cookie = jwtUtils.getCleanCookie();

        Map<String, Object> store = new HashMap<>();
        store.put("message", "You have been logged out successfully");

        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).body(store);
    }


    private String parseJwt(HttpServletRequest request) {
        String jwt = jwtUtils.getRefreshTokenFromCookie(request);
        return jwt;
    }

}
