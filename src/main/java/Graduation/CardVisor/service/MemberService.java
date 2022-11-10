package Graduation.CardVisor.service;


import Graduation.CardVisor.domain.member.Member;
import Graduation.CardVisor.domain.member.MyInfoDto;
import Graduation.CardVisor.repository.ColdStartRepository;
import Graduation.CardVisor.repository.MemberRepository;
import Graduation.CardVisor.repository.MyCardsRepository;
import Graduation.CardVisor.repository.ServiceTwoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService{

    private final MemberRepository memberRepository;
    private final ServiceTwoRepository serviceTwoRepository;
    private final MyCardsRepository myCardsRepository;
    private final ColdStartRepository coldStartRepository;
    private final AdminService adminService;

    public Member getMember(String nickname){
        return memberRepository.findByNickname(nickname).get();
    }

    public List<Member> getMembers(){
        return memberRepository.findAll();
    }

    public void changeMyInfo(MyInfoDto myInfoDto) {
        Long memberId = adminService.authenticate();
        Member member = memberRepository.findMemberById(memberId);
        member.setAge(Member.Age.valueOf(myInfoDto.getAge()));
        member.setGender(Member.Gender.valueOf(myInfoDto.getGender()));
        memberRepository.save(member);
    }

    public void withDraw() {
        Long memberId = adminService.authenticate();
        serviceTwoRepository.deleteAllByMemberId(memberId);
        coldStartRepository.deleteAllByMemberId(memberId);
        myCardsRepository.deleteAllByMemberId(memberId);
        memberRepository.deleteById(memberId);
    }
}
