package Graduation.CardVisor.domain.serviceone;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceOneCardsDto {
    private List<Long> cards;
}
