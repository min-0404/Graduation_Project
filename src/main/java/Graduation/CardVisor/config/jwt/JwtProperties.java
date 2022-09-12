package Graduation.CardVisor.config.jwt;

public interface JwtProperties {

    String SECRET = "CardvisorKey"; // JWT 토큰 이름
    int EXPIRATION_TIME = 60000*10; // JWT 토큰 유지시간
    String TOKEN_PREFIX = "Bearer "; // JWT 토큰 prefix 설정
    String HEADER_STRING = "Authorization"; // JWT 토큰 헤더 시작이름
}
