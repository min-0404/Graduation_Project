package Graduation.CardVisor.service;


import Graduation.CardVisor.domain.Card;
import Graduation.CardVisor.domain.benefit.BenefitDto;
import Graduation.CardVisor.domain.serviceone.ServiceOne;
import Graduation.CardVisor.domain.serviceone.ServiceOneCardsDto;
import Graduation.CardVisor.domain.serviceone.ServiceOneDto;
import Graduation.CardVisor.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

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

    private ServiceOneCardsDto resultDto = new ServiceOneCardsDto();

    private final CardService cardService;


    public void DtoToServiceOne(ServiceOneDto serviceOneDto) {

        ServiceOne serviceOne = new ServiceOne();

        serviceOne.setMember(memberRepository.getById(serviceOneDto.getMemberId()));
        serviceOne.setBrand(brandRepository.getByNameEngish(serviceOneDto.getBrandName()));

        serviceOneRepository.save(serviceOne);
    }

    // 추천 서비스 1 : 선택된 혜택들을 받아주는 컨트롤러
    public void saveSelections(List<ServiceOneDto> list){ // [{"memberId" : 1, "brandName": transport_bus}, {}, {}...] 형식으로 담겨져옴

        for(ServiceOneDto serviceOneDto : list){
            DtoToServiceOne(serviceOneDto); // Dto 를 정식 ServiceOne 객체로 변환해줌
        }


        // 매우매우매우매우매우매우 중요!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        // select 화면에서 result 화면으로 전환되면서, 플라스크로 신호 보내고 결과 받아오는 동작

        resultDto = flaskServiceOne();
    }


    // 추천 서비스 1
    // Spring 에서 Flask 로 Fetch 기능 : Flask 의 알고리즘이 작동되게 신호 보낸 다음, 추천된 카드 리스트를 받아오는 함수
    // 매우매우매우 중요
    public ServiceOneCardsDto flaskServiceOne() {
        var uri = UriComponentsBuilder.fromUriString("http://localhost:5001")
                .build()
                .encode()
                .toUri();

        var headers = new HttpHeaders();
        var httpEntity = new HttpEntity<>(headers);

        var responseType = new ParameterizedTypeReference<ServiceOneCardsDto>(){};
        var responseEntity = new RestTemplate().exchange(
                uri,
                HttpMethod.GET,
                httpEntity,
                responseType
        );
        return responseEntity.getBody();
    }


    public List<Card> dtoToRecommendedCards() {
        List<Card> cards = new ArrayList<>();

        for (Long cardId : resultDto.getCards()) {
            cards.add(cardRepository.findCardById(cardId));
        }

        return cards;
    }

    public List<BenefitDto> bestCardBenefits() {
        return cardService.getBenefits(resultDto.getCards().get(0));
    }


    public Integer bestCardLikeCount() {
        return cardService.getFavoriteCount(resultDto.getCards().get(0));
    }

}
