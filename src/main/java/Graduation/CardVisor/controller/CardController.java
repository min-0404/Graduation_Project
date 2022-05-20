package Graduation.CardVisor.controller;

import Graduation.CardVisor.domain.serviceone.ServiceOneDto;
import Graduation.CardVisor.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@Controller
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;

    // 한개의 카드 세부 페이지 컨트롤러
    @GetMapping("/{card_code}")
    public Map<String, Object> showCard(@PathVariable Long card_code){

        // 규칙: 컨트롤러 함수의 반환형은 무조건 HashMap<String(이름), Object(객체 및 리스트 및 맵)>
        Map<String, Object> store = new HashMap<>();

        store.put("card", cardService.getCard(card_code)); // <String, 객체>

        store.put("fee", cardService.getFee(card_code)); // <String, Map>

        store.put("category",cardService.getCategory(card_code)); // <String, Set>

        store.put("benefits", cardService.getBenefits(card_code)); // 모든 혜택 데이터 저장

        return store;
    }

    // 모든 카드 목록 페이지 컨트롤러
    @GetMapping("/cards")
    public Map<String, Object> showAllCards() {

        // 규칙 : 컨트롤러 함수의 반환형은 무조건 HashMap
        Map<String, Object> store = new HashMap<>();

        store.put("cardAll", cardService.getAllCards()); // <String, List>

        return store;
    }


}
