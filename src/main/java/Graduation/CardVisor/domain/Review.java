package Graduation.CardVisor.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity(name = "review")
@Data
public class Review {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "score")
    private Integer score;

    @Column(name = "short_review")
    private String short_reivew;

    @ManyToOne
    private Member member;

    @ManyToOne
    private Card card;

}
