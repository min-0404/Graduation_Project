package Graduation.CardVisor.controller;


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


    public List<Long> serviceOneCards() {
        var uri = UriComponentsBuilder.fromUriString("http://localhost:5001")
                .build()
                .encode()
                .toUri();

        var headers = new HttpHeaders();
        var httpEntity = new HttpEntity<>(headers);

        var responseType = new ParameterizedTypeReference<List<Long>>(){};
        var responseEntity = new RestTemplate().exchange(
                uri,
                HttpMethod.GET,
                httpEntity,
                responseType
        );
        return responseEntity.getBody();
    }

    public List<Long> results = new ArrayList<>();

    // 추천 서비스 1 : 선택된 혜택들을 받아주는 컨트롤러
    @CrossOrigin("http://localhost:3000") // method: post 로 받을 시 꼭 붙여줄 것
    @PostMapping("/select")
    public void getSelections(@RequestBody List<ServiceOneDto> list){ // [{"memberId" : 1, "brandName": transport_bus}, {}, {}...] 형식으로 담겨져옴

        for(ServiceOneDto serviceOneDto : list){
            benefitService.DtoToServiceOne(serviceOneDto); // Dto 를 정식 ServiceOne 객체로 변환해줌
        }

        results = serviceOneCards();
    }

    @GetMapping("/results")
    public List<Long> results() {

        for(Long result : results) {
            System.out.println(result);
        }

        return results;
    }

}
