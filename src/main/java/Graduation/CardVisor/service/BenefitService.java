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


    // 알아둘 것!!!!!!!!!!!!!!!!!!!!!!
    // ServiceOneDto 는 프론트엔드 <-> 스프링 백엔드 : 혜택 데이터 전달
    // ServiceOneCardsDto 는 스프링 백엔드 <-> 플라스크 백엔드 : 추천된 카드 데이터 전달

    private ServiceOneCardsDto resultDto = new ServiceOneCardsDto(); // Flask 서버에서 추천 된 카드들을 받아낼 임시 dto 를 전역변수로 선언

    private final CardService cardService;

    // 추천 서비스 1 : select 화면 -> result 화면 으로 넘어가는 클릭 한번으로 1단계와 2단계의 모든 과정이 자동으로 동작해야한다.
    public void saveSelections(List<ServiceOneDto> list){ // [{"memberId" : 1, "brandName": transport_bus}, {}, {}...] 형식으로 담겨져옴

        // 1단계: 일단, 프론트엔드에서 받아온 ServiceOneDto 를 ServiceOne 객체로 바꿔줘서 데이터베이스에 저장한다.
        for(ServiceOneDto serviceOneDto : list){
            DtoToServiceOne(serviceOneDto); // Dto 를 정식 ServiceOne 객체로 변환해줌
        }

        // 2단계: Flask 서버가 데이터베이스에 저장된 ServiceOne 데이터를 바탕으로 추천 알고리즘을 실행시키게 하고, 그 결과(ServiceOneCardsDto 형태)를 받아온다.
        resultDto = flaskServiceOne();
    }

    // 추천 서비스1 부가 함수 : 프론트에서 받아온 ServiceOneDto 를 ServiceOne 객체로 바꿔주는 함수
    public void DtoToServiceOne(ServiceOneDto serviceOneDto) {

        ServiceOne serviceOne = new ServiceOne();

        serviceOne.setMember(memberRepository.getById(serviceOneDto.getMemberId()));
        serviceOne.setBrand(brandRepository.getByNameEngish(serviceOneDto.getBrandName()));

        serviceOneRepository.save(serviceOne);
    }


    // 추천 서비스 1 부가 함수
    // Spring 에서 Flask 로 Fetch 기능 : Flask 의 알고리즘이 작동되게 신호 보낸 다음, 추천된 카드 리스트를 받아오는 함수
    // 매우매우매우 중요: Flask 에서 추천 된 카드 결과는 ServiceOneCardsDto 라는 객체 형태로 반환된다.
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

    // 추천 서비스1 부가 함수: ServiceOneCardsDto 객체의 카드 id를 바탕으로 실제 카드 객체들을 담은 리스트를 반환해주는 함수
    public List<Card> dtoToRecommendedCards() {
        List<Card> cards = new ArrayList<>();

        for (Long cardId : resultDto.getCards()) {
            cards.add(cardRepository.findCardById(cardId));
        }

        return cards;
    }

    // 추천 서비스 1 부가 함수: BestCard 의 혜택들을 뽑아서 리스트에 담아 반환해주는 함수
    public List<BenefitDto> bestCardBenefits() {
        return cardService.getBenefits(resultDto.getCards().get(0));
    }


    public Integer bestCardLikeCount() {
        return cardService.getFavoriteCount(resultDto.getCards().get(0));
    }

}
