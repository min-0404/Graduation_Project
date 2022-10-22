package Graduation.CardVisor.service;


import Graduation.CardVisor.domain.Card;
import Graduation.CardVisor.domain.MyCards;
import Graduation.CardVisor.domain.member.Member;
import Graduation.CardVisor.domain.serviceone.ServiceOneCardsDto;
import Graduation.CardVisor.domain.serviceone.ServiceOneDto;
import Graduation.CardVisor.repository.CardRepository;
import Graduation.CardVisor.repository.MemberRepository;
import Graduation.CardVisor.repository.MyCardsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecommendOneService {

    private final MemberRepository memberRepository;
    private final MyCardsRepository myCardsRepository;
    private final DtoService dtoService;
    private final CardRepository cardRepository;
    private final CardService cardService;
    private final AdminService adminService;

//=====서비스원 메인 함수==================================================================================================
    public Map<String, Object> recommend(List<ServiceOneDto> list){

        // 현재 로그인된 계정 확인
        Long memberId = adminService.authenticate();

        // 프론트에서 선택된 혜택들 DB에 저장
        saveSelections(list, memberId);

        // 추천 알고리즘 가동시키고 결과 받기
        ServiceOneCardsDto resultDto = flaskServiceOne(memberId);

        // 추천된 10개 Card 객체를 리스트에 저장
        List<Card> cards = new ArrayList<>();
        for(Long cardId : resultDto.getCards()){
            cards.add(cardRepository.findCardById(cardId));
        }

        // MyCards 테이블에 최고 카드만 저장
        MyCards myCards = new MyCards();
        myCards.setCard(cards.get(0));
        myCards.setMember(memberRepository.findMemberById(memberId));
        myCardsRepository.save(myCards);

        // 추천된 10개 카드객체와 최고 카드의 Benefit(BenefitDto) 해시맵으로 반환
        Map<String, Object> store = new HashMap<>();
        store.put("topTenCards", cards);
        store.put("bestCardBenefits", cardService.getBenefits(resultDto.getCards().get(0)));
        return store;
    }

    // 안쓰임
    public Map<String, Object> recommendMore(){

        // 현재 로그인된 계정 확인
        Long memberId = adminService.authenticate();

        // saveSelections()는 다시 실행할 필요 없음

        // 추천 알고리즘 가동시키고 결과 받기
        ServiceOneCardsDto resultDto = flaskServiceOne(memberId);

        // 추천된 10개 Card 객체를 리스트에 저장
        List<Card> cards = new ArrayList<>();
        for(Long cardId : resultDto.getCards()){
            cards.add(cardRepository.findCardById(cardId));
        }

        // 추천된 10개 카드객체와 최고 카드의 Benefit(BenefitDto) 해시맵으로 반환
        Map<String, Object> store = new HashMap<>();
        store.put("topTenCards", cards);
        return store;
    }

//=====서비스원 보조 함수==================================================================================================

    // 프론트에서 선택된 데이터 DB에 저장
    public void saveSelections(List<ServiceOneDto> list, Long id){

        for(ServiceOneDto serviceOneDto : list) {
            dtoService.DtoToServiceOne(serviceOneDto, id);
        }
    }

    // Flask 서버의 추천 알고리즘 가동시키고 결과 받아오기
    public ServiceOneCardsDto flaskServiceOne(Long id){

        // 요청 보낼 uri 설정
        var uri = UriComponentsBuilder.fromUriString("http://localhost:5001/api/serviceone/" + id)
                .build()
                .encode()
                .toUri();

        // HTTP 요청 및 응답 설정
        var headers = new HttpHeaders(); // 요청 헤더 설정
        var httpEntity = new HttpEntity<>(headers); // 요청 바디 설정
        var responseType = new ParameterizedTypeReference<ServiceOneCardsDto>(){}; // 응답 타입 설정
        var responseEntity = new RestTemplate().exchange( // 응답 바디 설정
                uri,
                HttpMethod.GET,
                httpEntity,
                responseType);

        // 응답 바디에 담긴 값 반환
        return responseEntity.getBody();
    }
}
