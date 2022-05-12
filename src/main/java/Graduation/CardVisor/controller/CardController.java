package Graduation.CardVisor.controller;

import Graduation.CardVisor.domain.ServiceOneDto;
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

    @GetMapping("/{card_code}")
    public Map<String, Object> getCard(@PathVariable Long card_code){

        // 규칙: 컨트롤러 함수의 반환형은 무조건 HashMap<String(이름), Object(객체 및 리스트 및 맵)>
        Map<String, Object> store = new HashMap<>();

        store.put("card", cardService.getCard(card_code)); // <String, 객체>

        store.put("fee", cardService.getFee(card_code)); // <String, Map>

        store.put("category",cardService.getCategory(card_code)); // <String, Set>

        store.put("benefits", cardService.getBenefits(card_code)); // 모든 혜택 데이터 저장

        return store;
    }

    @GetMapping("/cards")
    public Map<String, Object> showAllCards() {
        Map<String, Object> store = new HashMap<>();
        store.put("cardAll", cardService.getAllCards());
        return store;
    }
    @CrossOrigin("http://localhost:3000")
    @PostMapping("/select")
    public void getSelections(@RequestBody List<ServiceOneDto> list){
        for(ServiceOneDto serviceOneDto : list){
            System.out.println(serviceOneDto.getMemberId());
            System.out.println(serviceOneDto.getBrandId());
        }

    }
}
