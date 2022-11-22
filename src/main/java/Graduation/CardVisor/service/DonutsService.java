package Graduation.CardVisor.service;

import Graduation.CardVisor.domain.member.Member;
import Graduation.CardVisor.domain.servicetwo.ServiceTwo;
import Graduation.CardVisor.repository.MemberRepository;
import Graduation.CardVisor.repository.ServiceTwoRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Data
@RequiredArgsConstructor
@Service
@Slf4j
public class DonutsService {

    private final ServiceTwoRepository serviceTwoRepository;
    private final AdminService adminService;
    private final MemberRepository memberRepository;

    public List<Float> getDonuts() {

        // 현재 로그인된 계정 확인 및 나이, 성별 조회
        Long memberId = adminService.authenticate();
        Member.Age age = memberRepository.getMemberById(memberId).getAge();
        Member.Gender gender = memberRepository.getMemberById(memberId).getGender();

        List<Member> memberList = memberRepository.findByAgeAndGender(age, gender);

        int cnt = 0;

        // 로그인된 계정과 동일한 나이/성별의 cost 데이터를 평균내고 result 리스트에 담아 반환
        List<Float> result = new ArrayList<>(List.of(0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f, 0f));
        for (Member member : memberList) {
            List<ServiceTwo> costs = serviceTwoRepository.findByMemberId(member.getId());
            if (costs.isEmpty()) {
                continue;
            }
            for (int i = 0; i < 15; i++) {
                result.set(i, result.get(i) + costs.get(i).getCost());
            }
            cnt++;
        }
        for (int i = 0; i < 15; i++) {
            result.set(i, result.get(i) / cnt);
        }
        return result;
    }
}
