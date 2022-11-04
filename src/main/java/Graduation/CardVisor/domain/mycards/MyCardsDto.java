package Graduation.CardVisor.domain.mycards;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MyCardsDto {

    private Long cardId;
    private String cardName;
    private LocalDateTime date;
}
