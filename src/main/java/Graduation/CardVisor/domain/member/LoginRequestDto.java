package Graduation.CardVisor.domain.member;

import lombok.Data;


// 로그인시 시큐리티가 받아줄 로그인 데이터
@Data
public class LoginRequestDto {

    private String username; // Member 객체의 nickname 과 맵핑
    private String password; // Member 객체의 pw 와 맵핑
}
