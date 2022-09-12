package Graduation.CardVisor.domain;


import Graduation.CardVisor.domain.member.Member;
import lombok.Data;

import javax.persistence.*;

@Entity(name = "favorite")
@Data
public class Favorite {

    @Id
    @Column(name = "favorite_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_code")
    private Card card;
}
