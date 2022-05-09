package Graduation.CardVisor.controller;

import Graduation.CardVisor.domain.Benefit;
import Graduation.CardVisor.domain.Card;
import Graduation.CardVisor.domain.Category;
import Graduation.CardVisor.domain.Fee;
import Graduation.CardVisor.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
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

        Card card = cardService.getCard(card_code);
        store.put("card", card); // <String, 객체>

        Fee fee = cardService.getFee(card.getId());
        store.put("fee", cardService.getFeeValue(fee)); // <String, Map>

        Set<String> categories = cardService.getCategoryFromBenefit(card_code);
        store.put("category",categories); // <String, Set>

        List<Benefit> benefitList = cardService.getFilterdBenefit(card_code);
        store.put("benefits", benefitList); // 모든 혜택 데이터 저장

        return store;
    }
}
