package Graduation.CardVisor.service;


import Graduation.CardVisor.domain.benefit.Benefit;
import Graduation.CardVisor.domain.benefit.BenefitDto;
import Graduation.CardVisor.domain.serviceone.ServiceOne;
import Graduation.CardVisor.domain.serviceone.ServiceOneDto;
import Graduation.CardVisor.domain.servicetwo.ServiceTwo;
import Graduation.CardVisor.domain.servicetwo.ServiceTwoDto;
import Graduation.CardVisor.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// case1. Spring -> React 보내주기
// case2. React -> Spring 받아주기

@Service
@RequiredArgsConstructor
public class DtoService {

    private final MemberRepository memberRepository;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;
    private final ServiceOneRepository serviceOneRepository;
    private final ServiceTwoRepository serviceTwoRepository;

//=====case1. Spring -> React 보내주기 (객체명ToDto)=======================================================================-

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
//======case2. React -> Spring 받아주기 (DtoTo객체명)======================================================================

    // ServiceOneDto -> ServiceOne
    public void DtoToServiceOne(ServiceOneDto serviceOneDto, Long id) {

        ServiceOne serviceOne = new ServiceOne();
        serviceOne.setMember(memberRepository.getById(id));
        serviceOne.setBrand(brandRepository.getByNameEngish(serviceOneDto.getBrandName()));

        // DB에 저장
        serviceOneRepository.save(serviceOne);
    }

    // ServiceTwoDto -> ServiceTwo
    public void DtoToServiceTwo(ServiceTwoDto serviceTwoDto, Long id){

        ServiceTwo tmp = serviceTwoRepository.findByMemberAndCategory(memberRepository.getById(id), categoryRepository.findByName(serviceTwoDto.getCategoryName()));

        if(tmp == null) {
            ServiceTwo serviceTwo = new ServiceTwo();
            serviceTwo.setMember(memberRepository.getById(id));
            serviceTwo.setCategory(categoryRepository.findByName(serviceTwoDto.getCategoryName()));
            serviceTwo.setCost(serviceTwoDto.getCost());
            // DB에 저장
            serviceTwoRepository.save(serviceTwo);
        } else {
            ServiceTwo serviceTwo = tmp;
            serviceTwo.setCost(serviceTwoDto.getCost());
            // DB에 저장
            serviceTwoRepository.save(serviceTwo);
        }
    }
}
