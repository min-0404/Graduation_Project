package Graduation.CardVisor.controller;

import Graduation.CardVisor.domain.benefit.Benefit;
import Graduation.CardVisor.domain.Card;
import Graduation.CardVisor.domain.Fee;
import Graduation.CardVisor.domain.benefit.BenefitDto;
import Graduation.CardVisor.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@Controller
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;

    @GetMapping("/{card_code}")
    public Map<String, Object> getCard(@PathVariable Long card_code){

        // 규칙: 컨트롤러 함수의 반환형은 무조건 HashMap<String(이름), Object(객체 및 리스트 및 맵)>
        Map<String, Object> store = new HashMap<>();

        List<Card> card = cardService.singleCard(card_code);
        store.put("card", card); // <String, 객체>

        Fee fee = cardService.getFee(card_code);
        store.put("fee", cardService.getFeeValue(fee)); // <String, Map>

        Set<String> categories = cardService.getCategoryFromBenefit(card_code);
        store.put("category",categories); // <String, Set>

//        List<Benefit> benefitList = cardService.getFilteredBenefit(card_code);
        List<BenefitDto> benefitList = cardService.getWantedBenefits(card_code);
        store.put("benefits", benefitList); // 모든 혜택 데이터 저장

        return store;
    }

    @GetMapping("/{card_code}/card_api")
    public Map<String, Object> getCardInfo(@PathVariable Long card_code) {
        Map<String, Object> store = new HashMap<>();
        List<Card> card = cardService.singleCard(card_code);
        store.put("card", card);
        return store;
    }

    @GetMapping("/{card_code}/fee_api")
    public Map<String, Object> getFee(@PathVariable Long card_code) {
        Map<String, Object> store = new HashMap<>();
        Fee fee = cardService.getFee(card_code);
        store.put("fee", cardService.getFeeValue(fee));
        return store;
    }
}
