package Graduation.CardVisor.controller;

import Graduation.CardVisor.domain.serviceone.ServiceOneDto;
import Graduation.CardVisor.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/card")
public class CardController {

    private final CardService cardService;

    // 단일 카드 조회 페이지
    @GetMapping("/{card_code}")
    public Map<String, Object> showCard(@PathVariable Long card_code){

        Map<String, Object> store = new HashMap<>();
        store.put("card", cardService.getCard(card_code)); // {String, Card}
        store.put("fee", cardService.getFee(card_code)); // {String, Map}
        store.put("category",cardService.getCategory(card_code)); // {String, Set}
        store.put("benefits", cardService.getBenefits(card_code)); // 모든 혜택 데이터 저장

        return store;
    }

    // 모든 카드 조회 페이지
    @GetMapping("/cards")
    public Map<String, Object> showAllCards() {

        Map<String, Object> store = new HashMap<>();
        store.put("cardAll", cardService.getAllCards()); // {String, List}

        return store;
    }
}
