package Graduation.CardVisor.domain.benefit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BenefitDto {

    // Spring -> React
    // serviceone 결과화면에서 "1등 추천 카드"의 정보 중 원하는 정보만 선택해서 내려주기 위한 Dto 객체
    // servicetwo 결과화면에서 "1등 추천 카드"의 정보 중 원하는 정보만 선택해서 내려주기 위한 Dto 객체
    private String categoryName;
    private String brandName;
    private String brandNameKor;
    private String feeType;
    private Float numberOne;
    private Float numberTwo;
}
