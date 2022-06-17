package Graduation.CardVisor.domain.benefit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BenefitDto {

    private String categoryName;
    private String brandName;
    private String brandNameKor;
    private String feeType;
    private Float numberOne;
    private Float numberTwo;
}
