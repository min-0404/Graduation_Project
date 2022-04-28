package Graduation.CardVisor.domain;

import javax.persistence.*;

@Entity(name = "servicetwo")
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
    private Integer cost;
}
