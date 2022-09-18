package Graduation.CardVisor.domain.serviceone;

import Graduation.CardVisor.domain.Brand;
import Graduation.CardVisor.domain.member.Member;
import lombok.Data;

import javax.persistence.*;

@Entity(name = "serviceone")
@Data
public class ServiceOne {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "serviceone_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private Brand brand;
}
