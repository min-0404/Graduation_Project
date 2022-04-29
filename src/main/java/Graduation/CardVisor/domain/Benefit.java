package Graduation.CardVisor.domain;


import lombok.Data;

import javax.persistence.*;

@Entity(name = "benefit")
@Data
public class Benefit {

    @Id
    @Column(name = "benefit_id")
    private Long id;

    @Column(name = "benefit_type")
    private String type;

    @Column(name = "benefit_1")
    private Integer number1;

    @Column(name = "benefit_2")
    private Integer number2;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "card_code")
    private Card card;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private Brand brand;
}
