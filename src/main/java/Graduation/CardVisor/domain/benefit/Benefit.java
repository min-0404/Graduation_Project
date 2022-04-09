package Graduation.CardVisor.domain.benefit;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class Benefit {
    @GeneratedValue
    @Id
    private Long benefit_Id;


    private String type;

    private Integer number1;

    private Integer number2;



}

