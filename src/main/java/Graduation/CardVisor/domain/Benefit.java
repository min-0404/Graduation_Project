package Graduation.CardVisor.domain;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity(name = "benefit")
@Data
public class Benefit {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "type")
    private String type;

    @Column(name = "benefit_1")
    private Integer number1;

    @Column(name = "benefit_2")
    private Integer number2;

    @ManyToOne
    private Card card;

    @ManyToOne
    private Category category;

    @ManyToOne
    private Brand brand;

}
