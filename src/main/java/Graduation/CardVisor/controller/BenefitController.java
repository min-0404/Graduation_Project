package Graduation.CardVisor.controller;


import Graduation.CardVisor.domain.serviceone.ServiceOneCardsDto;
import Graduation.CardVisor.domain.serviceone.ServiceOneDto;
import Graduation.CardVisor.service.BenefitService;
import Graduation.CardVisor.service.CardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RestController
@RequiredArgsConstructor
public class BenefitController {

    private final CardService cardService;
    private final BenefitService benefitService;

    ServiceOneCardsDto results = new ServiceOneCardsDto(); // 추천된 카드 리스트를 담아주는 Dto


    // 추천 서비스 1
    // Spring 에서 Flask 로 Fetch 기능 : Flask 의 알고리즘이 작동되게 신호 보낸 다음, 추천된 카드 리스트를 받아오는 함수
    // 매우매우매우 중요
    public ServiceOneCardsDto serviceOneCards() {
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



    // 추천 서비스 1 : 선택된 혜택들을 받아주는 컨트롤러
    @CrossOrigin("http://localhost:3000") // method: post 로 받을 시 꼭 붙여줄 것
    @PostMapping("/select")
    public void getSelections(@RequestBody List<ServiceOneDto> list){ // [{"memberId" : 1, "brandName": transport_bus}, {}, {}...] 형식으로 담겨져옴

        for(ServiceOneDto serviceOneDto : list){
            benefitService.DtoToServiceOne(serviceOneDto); // Dto 를 정식 ServiceOne 객체로 변환해줌
        }


        // 매우매우매우매우매우매우 중요!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        // select 화면에서 result 화면으로 전환되면서, 플라스크로 신호 보내고 결과 받아오는 동작
        results = serviceOneCards();
    }

    // 추천 서비스 1: 추천된 카드들의 정보를 프론트로 전송하는 컨트롤러
    @GetMapping("/results")
    public ServiceOneCardsDto results() {
        return results;

        // 시험끝난 우리에게....
        // results 에는 추천된 카드들이 담겨있다. => 이 카드들의 데이터 뽑아서 정리해서 프론트로 보내주는 것부터 시작하면 된단다. (그냥 showAllCards 처럼 하면 됨)
    }

}
