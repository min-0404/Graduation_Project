package Graduation.CardVisor.controller;


import Graduation.CardVisor.domain.member.Member;
import Graduation.CardVisor.domain.member.MyInfoDto;
import Graduation.CardVisor.repository.MemberRepository;
import Graduation.CardVisor.service.AdminService;
import Graduation.CardVisor.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    private final AdminService adminService;

    private final MemberRepository memberRepository;
    @GetMapping("/username")
    public String memberUsername() {

        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();

        return loggedInUser.getName();
    }

    @GetMapping("/showMyInfo")
    public ResponseEntity<?> showAgeGender() {
        Long memberId = adminService.authenticate();
        Member member = memberRepository.findMemberById(memberId);
        Map<String, String> store = new HashMap<>();
        store.put("gender", member.getGender().toString());
        store.put("age", member.getAge().toString());
        return ResponseEntity.ok().body(store);
    }

    @PostMapping("/myInfo")
    public void changeAgeGender(@RequestBody MyInfoDto myInfoDto) {
        memberService.changeMyInfo(myInfoDto);
    }
}
