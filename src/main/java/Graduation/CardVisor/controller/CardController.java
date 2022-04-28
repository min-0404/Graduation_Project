package Graduation.CardVisor.controller;

import Graduation.CardVisor.domain.Card;
import Graduation.CardVisor.domain.Fee;
import Graduation.CardVisor.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class CardController {
    private final CardService cardService;

    @GetMapping("/{card_code}")
    public String getCard(@PathVariable Long card_code, Model model){
        Card card = cardService.getCard(card_code);
        model.addAttribute("card", card);

        Fee fee = cardService.getFee(card);
        model.addAttribute("fee_values", cardService.getFeeValue(fee));

        model.addAttribute("categories", cardService.getCategoryFromBenefit(card.getId()));
        return "practice/1";
    }


}
