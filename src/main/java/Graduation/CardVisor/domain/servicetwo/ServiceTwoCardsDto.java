package Graduation.CardVisor.domain.servicetwo;

import lombok.Data;

import java.util.List;

@Data
public class ServiceTwoCardsDto {

    // Flask -> Spring
    // 플라스크 서버에서 추천된 20개 카드의 id 를 받아 줄 리스트
    private List<Long> cards;
}
