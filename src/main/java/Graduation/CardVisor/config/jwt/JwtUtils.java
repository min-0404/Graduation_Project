package Graduation.CardVisor.config.jwt;

import Graduation.CardVisor.config.userDetails.UserDetailsImpl;
import Graduation.CardVisor.service.MemberService;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
public class JwtUtils {

    private final MemberService memberService;

    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("CardVisorSecret")
    private String jwtSecret;

    @Value("600000")
    private int jwtExpirationMs;

    @Value("refreshToken")
    private String jwtCookie;

    @Value("Bearer ")
    private String tokenPrefix;

    public String getTokenPrefix() {
        return tokenPrefix;
    }

    //    String TOKEN_PREFIX = "Bearer ";
//    String HEADER_STRING = "Authorization";

    public String getRefreshTokenFromCookie(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, jwtCookie);
        if(cookie != null) {
            return cookie.getValue();
        } else {
            return null;
        }
    }

    public ResponseCookie generateRefreshTokenCookie(UserDetailsImpl userDetails, StringBuffer requestURL) {
        String jwt = generateRefreshTokenFromMember(userDetails, requestURL);
        ResponseCookie cookie = ResponseCookie.from(jwtCookie, jwt)
                .path("/")
                .maxAge(24 * 60 * 60)
                .httpOnly(true)
                .sameSite("None")
                .build();
        return cookie;
    }

    public ResponseCookie getCleanCookie() {
        ResponseCookie cookie = ResponseCookie.from(jwtCookie, null).path("/").httpOnly(true).build();
        return cookie;
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
    }


    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is emtpy: {}", e.getMessage());
        }
        return false;
    }


    public String generateAccessTokenFromMember(UserDetailsImpl userDetails) {


        Map<String, Object> map = new HashMap<>();
        map.put("roles", userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
        return Jwts.builder()
                .setClaims(map)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
//                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs/120))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .setIssuer("cardvisor.ga")
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }



    private String generateRefreshTokenFromMember(UserDetailsImpl userDetails, StringBuffer requestURL) {
//        return JWT.create()
//                .withSubject(member.getNickname())
//                .withExpiresAt(new Date(System.currentTimeMillis() + jwtExpirationMs))
//                .withIssuer(request.getRequestURI().toString())
//                .withClaim("roles", userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
//                .sign(Algorithm.HMAC512(jwtSecret));
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
//                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs/60 + 5000))
                .setExpiration(new Date(System.currentTimeMillis() + 12L * jwtExpirationMs))
                .setIssuer(requestURL.toString())
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }


}
