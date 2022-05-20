package Graduation.CardVisor.service;


import Graduation.CardVisor.domain.serviceone.ServiceOne;
import Graduation.CardVisor.domain.serviceone.ServiceOneDto;
import Graduation.CardVisor.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class BenefitService {
    private final CardRepository cardRepository;
    private final MemberRepository memberRepository;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;
    private final FeeRepository feeRepository;
    private final FavoriteRepository favoriteRepository;
    private final ReviewRepository reviewRepository;
    private final ServiceOneRepository serviceOneRepository;
    private final ServiceTwoRepository serviceTwoRepository;
    private final BenefitRepository benefitRepository;


    public void DtoToServiceOne(ServiceOneDto serviceOneDto) {

        ServiceOne serviceOne = new ServiceOne();

        serviceOne.setMember(memberRepository.getById(serviceOneDto.getMemberId()));
        serviceOne.setBrand(brandRepository.getByNameEngish(serviceOneDto.getBrandName()));

        serviceOneRepository.save(serviceOne);
    }


}
