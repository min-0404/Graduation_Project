package Graduation.CardVisor.domain.serviceone;


import lombok.Data;

import java.util.List;

@Data
public class ServiceOneCardsDto {

    // Flask -> Spring
    // 플라스크 서버에서 추천된 20개의 카드id를 받아 줄 Dto 리스트 객체
    private List<Long> cards;
}
