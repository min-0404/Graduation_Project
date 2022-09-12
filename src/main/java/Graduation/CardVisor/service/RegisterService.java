package Graduation.CardVisor.service;

import Graduation.CardVisor.domain.member.Member;
import Graduation.CardVisor.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class RegisterService {

    private final BCryptPasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    public Member saveMember(Member member){
        if(!memberRepository.existsByNickname(member.getNickname())){
            member.setPw(passwordEncoder.encode(member.getPw()));
            member.setRoles("ROLE_USER");

            return memberRepository.save(member); // 리턴값 굳이 설정한 이유: LoginController 에서 join() 로직을 단순화하기 위해
        }
        else{
            return null;
        }
    }

    public ResponseEntity<String> checkDuplicateNickname(String nickname){
        if(!memberRepository.existsByNickname(nickname)){
            return ResponseEntity.ok().body("사용가능한 아이디입니다.");
        }
        else{
            return ResponseEntity.badRequest().body("이미 사용중인 아이디입니다.");
        }
    }
}
