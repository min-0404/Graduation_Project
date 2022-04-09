package Graduation.CardVisor.domain.card;

import Graduation.CardVisor.domain.benefit.Benefit;
import lombok.Data;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Data
public abstract class Card {

    @Id
    @Column(name = "card_id")
    private Long id;

    private String name;

    private String provider;

    private String link;

    @ManyToOne
    private Benefit benefit;

}