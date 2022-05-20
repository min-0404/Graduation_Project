package Graduation.CardVisor.controller;


import Graduation.CardVisor.domain.serviceone.ServiceOneDto;
import Graduation.CardVisor.service.BenefitService;
import Graduation.CardVisor.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Controller
@RestController
@RequiredArgsConstructor
public class BenefitController {

    private final CardService cardService;
    private final BenefitService benefitService;


    // 추천 서비스 1 : 선택된 혜택들을 받아주는 컨트롤러
    @CrossOrigin("http://localhost:3000") // method: post 로 받을 시 꼭 붙여줄 것
    @PostMapping("/select")
    public void getSelections(@RequestBody List<ServiceOneDto> list){ // [{"memberId" : 1, "brandName": transport_bus}, {}, {}...] 형식으로 담겨져옴

        for(ServiceOneDto serviceOneDto : list){
            benefitService.DtoToServiceOne(serviceOneDto); // Dto 를 정식 ServiceOne 객체로 변환해줌
        }
    }

}
