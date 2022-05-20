package Graduation.CardVisor.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "card")
public class Card {

    @Id
    @Column(name = "card_code")
    private Long id;

    @Column(name = "card_name")
    private String name;

    @Column(name = "card_company")
    private String company;

    @Column(name = "card_type")
    private String type;

    @Column(name = "card_link")
    private String link;

    @Column(name = "card_company_eng")
    private String company_eng;

}