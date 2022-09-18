package Graduation.CardVisor.domain.servicetwo;

import Graduation.CardVisor.domain.Brand;
import Graduation.CardVisor.domain.member.Member;
import lombok.Data;

import javax.persistence.*;

@Entity(name = "servicetwo")
@Data
public class ServiceTwo {

    @Id
    @Column(name = "servicetwo_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @Column(name = "cost")
    private Float cost;
}
