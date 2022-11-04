package Graduation.CardVisor.controller;


import Graduation.CardVisor.domain.member.MyInfoDto;
import Graduation.CardVisor.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;
    @GetMapping("/username")
    public String memberUsername() {

        Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();

        return loggedInUser.getName();
    }

    @PostMapping("/myInfo")
    public void changeAgeGender(@RequestBody MyInfoDto myInfoDto) {
        memberService.changeMyInfo(myInfoDto);
    }
}
