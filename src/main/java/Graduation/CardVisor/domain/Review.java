package Graduation.CardVisor.domain;

import lombok.Data;

import javax.persistence.*;

@Entity(name = "review")
@Data
public class Review {

    @Id
    @Column(name = "review_id")
    private Long id;

    @Column(name = "score")
    private Integer score;

    @Column(name = "short_review")
    private String short_reivew;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_code")
    private Card card;

}
