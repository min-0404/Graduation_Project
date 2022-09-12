package Graduation.CardVisor.domain.benefit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BenefitDto {

    // Spring -> React
    // 서비스원, 서비스투 결과 화면에서 "1등 카드"의 필요한 정보만 내려주기 위한 Dto
    private String categoryName;
    private String brandName;
    private String brandNameKor;
    private String feeType;
    private Float numberOne;
    private Float numberTwo;
}
