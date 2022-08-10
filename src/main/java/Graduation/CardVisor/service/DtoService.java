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

// API 처리 3가지 case
// case1. Spring -> React 보내주기
// case2. React -> Spring 받아주기
// case3. Flask -> Spring 받아주기


@Service
@RequiredArgsConstructor
public class DtoService {

    private final MemberRepository memberRepository;
    private final BrandRepository brandRepository;
    private final ServiceOneRepository serviceOneRepository;


    // case1. Spring -> React 보내주기: 객체명ToDto

    // Benefit -> BenefitDto
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

    // case2. React -> Spring 받아주기: DtoTo객체명

    // ServiceOneDto -> ServiceOne
    public void DtoToServiceOne(ServiceOneDto serviceOneDto) {

        ServiceOne serviceOne = new ServiceOne();

        serviceOne.setMember(memberRepository.getById(serviceOneDto.getMemberId()));
        serviceOne.setBrand(brandRepository.getByNameEngish(serviceOneDto.getBrandName()));

        serviceOneRepository.save(serviceOne);
    }

}

//---------------------------------------------------------------------------------------------------------------------

    // case3. Flask -> Spring 받아주기: DtoTo객체명