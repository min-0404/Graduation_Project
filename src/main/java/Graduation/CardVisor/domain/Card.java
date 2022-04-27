package Graduation.CardVisor.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "card")
public class Card {

    @Id
    @Column(name = "code")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "company")
    private String company;

    @Column(name = "type")
    private String type;

    @Column(name = "link")
    private String link;
}