package Graduation.CardVisor.domain.servicetwo;


import lombok.Data;

@Data
public class ServiceTwoDto {

    // React -> Spring
    // servicetwo 화면에서 사용자가 선택한 한개의 브랜드
    // 사용자가 여러개의 브랜드를 선택하면, 이것들이 모여서 servicetwo 테이블에 저장 됨
    private Long memberId;
    private String categoryName;
    private Float cost;
}

