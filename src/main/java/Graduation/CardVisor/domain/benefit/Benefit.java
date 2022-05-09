package Graduation.CardVisor.domain.benefit;


import Graduation.CardVisor.domain.Brand;
import Graduation.CardVisor.domain.Card;
import Graduation.CardVisor.domain.Category;
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
    private Float number1;

    @Column(name = "benefit_2")
    private Float number2;

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
