package Graduation.CardVisor.domain.serviceone;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceOneDto {

    // React -> Spring
    // serviceone 화면에서 사용자가 선택한 "하나의 혜택"
    // 사용자가 여러개의 혜택을 선택할 시 얘네들이 여러개 모여서 serviceone 테이블에 저장 됨
    private Long memberId;
    private String brandName;
}
