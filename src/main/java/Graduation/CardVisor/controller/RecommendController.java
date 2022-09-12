package Graduation.CardVisor.controller;


import Graduation.CardVisor.domain.serviceone.ServiceOneDto;
import Graduation.CardVisor.domain.servicetwo.ServiceTwoDto;
import Graduation.CardVisor.service.BenefitService;
import Graduation.CardVisor.service.CardService;
import Graduation.CardVisor.service.RecommendOneService;
import Graduation.CardVisor.service.RecommendTwoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/benefit")
public class RecommendController {

    private final CardService cardService;
    private final RecommendOneService recommendOneService;
    private final RecommendTwoService recommendTwoService;

    // select -> recommendOne
    @PostMapping("/select")
    public Map<String, Object> recommendOne(@RequestBody List<ServiceOneDto> list) { // [{"memberId" : 1, "brandName": transport_bus}, {}, {}...] 형식으로 담겨져옴
        return recommendOneService.recommend(list);
    }

    // resultMore -> recommendOneMore
    @GetMapping("/resultMore")
    public Map<String, Object> recommendOneMore(){
        return recommendOneService.recommendMore();
    }

    @PostMapping("/recommendTwo")
    public Map<String, Object> recommendTwo(@RequestBody List<ServiceTwoDto> list){
        return recommendTwoService.recommend(list);
    }

    @GetMapping("/recommendTwoMore")
    public Map<String, Object> recommendTwoMore(){
        return recommendTwoService.recommendMore();
    }
}
