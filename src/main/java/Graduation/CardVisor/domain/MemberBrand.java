package Graduation.CardVisor.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity(name = "member_brand")
@Data
public class MemberBrand {

    @Id
    @Column(name = "id")
    private Long id;

    @ManyToOne
    private Member member;

    @ManyToOne
    private Brand brand;
}
