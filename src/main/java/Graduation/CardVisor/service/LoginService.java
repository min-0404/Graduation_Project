package Graduation.CardVisor.service;

import Graduation.CardVisor.domain.member.Member;
import Graduation.CardVisor.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginService implements UserDetailsService {

    private final MemberRepository memberRepository;

    // CustomAuthenticationFilter 의 attemptAuthentication() 에서 자동으로 실행되는 함수
    // loginRequestDto 에 입력된 username 을 DB에 저장된 Member 객체의 nickname 과 비교한 뒤, User 객체 생성
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // username 으로 Member 객체 조회
        Member member = memberRepository.findByNickname(username);
        if(member == null) {
            log.error("User not found in the database");
            throw new UsernameNotFoundException("User not found in the database");
        } else {
            log.info("User found in the database: {}", username);
        }

        // 찾아낸 Member 객체의 role 조회
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        member.getRolesList().forEach(role->{
            authorities.add(new SimpleGrantedAuthority(role));
        });

        // Member 객채의 (nickname + pw + role) 을 바탕으로 User 객체 생성하고 반환
        return new org.springframework.security.core.userdetails.User(member.getNickname(), member.getPw(), authorities);
    }
}
