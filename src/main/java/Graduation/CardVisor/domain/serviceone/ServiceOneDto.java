package Graduation.CardVisor.domain.serviceone;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceOneDto {
    private Long memberId;
    private String brandName;
}
