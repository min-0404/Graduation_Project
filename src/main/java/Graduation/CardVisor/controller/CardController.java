package Graduation.CardVisor.controller;

import Graduation.CardVisor.domain.Card;
import Graduation.CardVisor.domain.Category;
import Graduation.CardVisor.domain.Fee;
import Graduation.CardVisor.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    public List<Object> getCard(@PathVariable Long card_code){

        List<Object> store = new ArrayList<>();

        Card card = cardService.getCard(card_code);
        store.add(card); // 카드 데이터 저장

        Fee fee = cardService.getFee(card.getId());
        store.add(cardService.getFeeValue(fee)); // 연회비 데이터 저장

        Set<String> categories = cardService.getCategoryFromBenefit(card_code);
        Map<Integer, String> map = new HashMap<>();
        int i =0;
        for(String s:categories){
            map.put(i,s);
            i++;
        }
        store.add(map); // 카테고리 리스트 데이터 저장

        Map<String, Object> benefitList = cardService.showWantedBenefits(card_code);
        store.add(benefitList); // 모든 혜택 데이터 저장

        return store;
    }


}
