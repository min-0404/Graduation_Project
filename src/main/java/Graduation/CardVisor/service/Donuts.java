package Graduation.CardVisor.service;

import Graduation.CardVisor.domain.servicetwo.ServiceTwo;
import Graduation.CardVisor.repository.MemberRepository;
import Graduation.CardVisor.repository.ServiceTwoRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static Graduation.CardVisor.domain.member.Member.Age.one;

@Data
@RequiredArgsConstructor
@Service
@Slf4j
public class Donuts {

    private final ServiceTwoRepository serviceTwoRepository;
    private final AdminService adminService;
    private final MemberRepository memberRepository;

    public List<Float> getDonuts() {

        // 현재 로그인된 계정 확인 및 나이, 성별 조회
        Long memberId = adminService.authenticate();
        String age = String.valueOf(memberRepository.getMemberById(memberId).getAge());
        String gender = String.valueOf(memberRepository.getMemberById(memberId).getGender());

        // 로그인된 계정과 동일한 나이/성별의 cost 데이터를 result 리스트에 담아 반환
        List<Float> result = new ArrayList<>();
        if (age.equals("one")) {
            if (gender.equals("male")) {
                List<ServiceTwo> costs = serviceTwoRepository.findByMemberId(201901100L);
                for (ServiceTwo s : costs) {
                    result.add(s.getCost());
                }
            } else {
                List<ServiceTwo> costs = serviceTwoRepository.findByMemberId(201901101L);
                for (ServiceTwo s : costs) {
                    result.add(s.getCost());
                }
            }
        }
        if (age.equals("two")) {
            if (gender.equals("male")) {
                List<ServiceTwo> costs = serviceTwoRepository.findByMemberId(201901200L);
                for (ServiceTwo s : costs) {
                    result.add(s.getCost());
                }
            } else {
                List<ServiceTwo> costs = serviceTwoRepository.findByMemberId(201901201L);
                for (ServiceTwo s : costs) {
                    result.add(s.getCost());
                }
            }
        }
        if (age.equals("three")) {
            if (gender.equals("male")) {
                List<ServiceTwo> costs = serviceTwoRepository.findByMemberId(201930010L);
                for (ServiceTwo s : costs) {
                    result.add(s.getCost());
                }
            } else {
                List<ServiceTwo> costs = serviceTwoRepository.findByMemberId(201930011L);
                for (ServiceTwo s : costs) {
                    result.add(s.getCost());
                }
            }
        }
        if (age.equals("four")) {
            if (gender.equals("male")) {
                List<ServiceTwo> costs = serviceTwoRepository.findByMemberId(201901400L);
                for (ServiceTwo s : costs) {
                    result.add(s.getCost());
                }
            } else {
                List<ServiceTwo> costs = serviceTwoRepository.findByMemberId(201901401L);
                for (ServiceTwo s : costs) {
                    result.add(s.getCost());
                }
            }
        }
        if (age.equals("five")) {
            if (gender.equals("male")) {
                List<ServiceTwo> costs = serviceTwoRepository.findByMemberId(201901500L);
                for (ServiceTwo s : costs) {
                    result.add(s.getCost());
                }
            } else {
                List<ServiceTwo> costs = serviceTwoRepository.findByMemberId(201901501L);
                for (ServiceTwo s : costs) {
                    result.add(s.getCost());
                }
            }
        }
        if (age.equals("six")) {
            if (gender.equals("male")) {
                List<ServiceTwo> costs = serviceTwoRepository.findByMemberId(201901600L);
                for (ServiceTwo s : costs) {
                    result.add(s.getCost());
                }
            } else {
                List<ServiceTwo> costs = serviceTwoRepository.findByMemberId(201901601L);
                for (ServiceTwo s : costs) {
                    result.add(s.getCost());
                }
            }
        }
        log.info(age);
        log.info(gender);
        return result;
    }
}
