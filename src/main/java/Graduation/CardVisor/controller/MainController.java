package Graduation.CardVisor.controller;

import Graduation.CardVisor.domain.mycards.MyCards;
import Graduation.CardVisor.domain.mycards.MyCardsDto;
import Graduation.CardVisor.service.MyCardsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/main")
public class MainController {

    private final MyCardsService myCardsService;

    @GetMapping("/myCards")
    public ResponseEntity<Map<String, List<MyCardsDto>>> showMyCards() {
        Map<String, List<MyCardsDto>> myCardsMap = new HashMap<>();
        myCardsMap.put("MyCards", myCardsService.getMyCards());
        return ResponseEntity.ok().body(myCardsMap);
    }
}
