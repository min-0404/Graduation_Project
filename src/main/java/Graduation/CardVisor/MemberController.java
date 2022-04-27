package Graduation.CardVisor;

import Graduation.CardVisor.domain.Card;
import Graduation.CardVisor.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final CardRepository cardRepository;
    @ResponseBody
    @GetMapping("/card")
    public String getCard(){

        Card card = cardRepository.find(16L);
        return card.getCompany();
    }
}
