package Graduation.CardVisor.controller;


import Graduation.CardVisor.domain.serviceone.ServiceOneDto;
import Graduation.CardVisor.domain.servicetwo.ServiceTwoDto;
import Graduation.CardVisor.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/benefit")
public class RecommendController {

    private final RecommendOneService recommendOneService;
    private final RecommendTwoService recommendTwoService;
    private final Donuts donuts;

    // select -> recommendOne
    @PostMapping("/select")
    public Map<String, Object> recommendOne(@RequestBody List<ServiceOneDto> list) { // [{"memberId" : 1, "brandName": transport_bus}, {}, {}...] 형식으로 담겨져옴
        return recommendOneService.recommend(list);
    }

    // resultMore -> recommendOneMore
//    @GetMapping("/resultMore")
//    public Map<String, Object> recommendOneMore(){
//        return recommendOneService.recommendMore();
//    }

    @PostMapping("/recommendTwo")
    public Map<String, Object> recommendTwo(@RequestBody List<ServiceTwoDto> list){
        return recommendTwoService.recommend(list);
    }

    @GetMapping("/recommendTwoMore")
    public Map<String, Object> recommendTwoMore(){
        return recommendTwoService.recommendMore();
    }


    @GetMapping("/donuts")
    public ResponseEntity<Map<String, List<Float>>> showDonuts(){

        Map<String, List<Float>> donutsMap = new HashMap<>();
        List<Float> donutsList = donuts.getDonuts();
        donutsMap.put("result", donutsList);
        return ResponseEntity.ok().body(donutsMap);
    }
}
