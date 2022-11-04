package Graduation.CardVisor.service;

import Graduation.CardVisor.domain.mycards.MyCards;
import Graduation.CardVisor.domain.mycards.MyCardsDto;
import Graduation.CardVisor.repository.MemberRepository;
import Graduation.CardVisor.repository.MyCardsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MyCardsService {

    private final AdminService adminService;
    private final MyCardsRepository myCardsRepository;
    private final MemberRepository memberRepository;

    public List<MyCardsDto> getMyCards() {
        Long memberId = adminService.authenticate();
        List<MyCards> myCards = myCardsRepository.getByMemberId(memberId);
        List<MyCardsDto> myCardsDtoList = new ArrayList<>();
        for (MyCards card : myCards)
        {
            MyCardsDto myCardsDto = new MyCardsDto();
            LocalDateTime date = card.getDate();
            Long cardId = card.getCard().getId();
            String cardName = card.getCard().getName();
            myCardsDto.setCardId(cardId);
            myCardsDto.setCardName(cardName);
            myCardsDto.setDate(date);
            myCardsDtoList.add(myCardsDto);
        }
        return myCardsDtoList;
    }
}
