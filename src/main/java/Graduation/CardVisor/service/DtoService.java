package Graduation.CardVisor.service;


import Graduation.CardVisor.domain.benefit.Benefit;
import Graduation.CardVisor.domain.benefit.BenefitDto;
import Graduation.CardVisor.domain.serviceone.ServiceOne;
import Graduation.CardVisor.domain.serviceone.ServiceOneDto;
import Graduation.CardVisor.repository.BrandRepository;
import Graduation.CardVisor.repository.MemberRepository;
import Graduation.CardVisor.repository.ServiceOneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DtoService {

    private final MemberRepository memberRepository;
    private final BrandRepository brandRepository;
    private final ServiceOneRepository serviceOneRepository;


    // 백엔드 -> 프론트엔드로 전송하는 경우: 객체명ToDto



    // 4-1. Benefit -> BenefitDto 로 변환 (API 서버 -> 프론트로 내려줄 때 Dto 로 바꿔서 내려주기 왜였지??)
    public BenefitDto benefitToDto(Benefit benefit) {

        BenefitDto benefitDto = new BenefitDto();

        benefitDto.setCategoryName(benefit.getCategory().getName());
        benefitDto.setBrandName(benefit.getBrand().getNameEngish());
        benefitDto.setBrandNameKor(benefit.getBrand().getNameKorean());
        benefitDto.setFeeType(benefit.getType());
        benefitDto.setNumberOne(benefit.getNumber1());
        benefitDto.setNumberTwo(benefit.getNumber2());

        return benefitDto;
    }
//---------------------------------------------------------------------------------------------------------------------

    // 프론트엔드 -> 백엔드로 전송받는 경우: DtoTo객체명

    // 추천 서비스1 부가 함수 : 프론트에서 받아온 ServiceOneDto 를 ServiceOne 객체로 바꿔주는 함수
    public void DtoToServiceOne(ServiceOneDto serviceOneDto) {

        ServiceOne serviceOne = new ServiceOne();

        serviceOne.setMember(memberRepository.getById(serviceOneDto.getMemberId()));
        serviceOne.setBrand(brandRepository.getByNameEngish(serviceOneDto.getBrandName()));

        serviceOneRepository.save(serviceOne);
    }

}
