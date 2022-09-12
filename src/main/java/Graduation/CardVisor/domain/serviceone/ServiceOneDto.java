package Graduation.CardVisor.domain.serviceone;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceOneDto {

    // React -> Spring
    // serviceone 화면에서 사용자가 선택한 한개의 브랜드
    // 사용자가 여러개의 브랜드를 선택하면, 이것들이 모여서 serviceone 테이블에 저장 됨
    private Long memberId;
    private String brandName;
}
