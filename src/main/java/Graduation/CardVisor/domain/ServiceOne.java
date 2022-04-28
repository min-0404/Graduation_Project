package Graduation.CardVisor.domain;

import lombok.Data;

import javax.persistence.*;

@Entity(name = "serviceone")
@Data
public class ServiceOne {

    @Id
    @Column(name = "serviceone_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private Brand brand;
}
