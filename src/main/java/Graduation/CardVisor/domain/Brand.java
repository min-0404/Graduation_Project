package Graduation.CardVisor.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity(name = "brand")
@Data
public class Brand {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "name_eng")
    private String nameEngish;

    @Column(name = "name_kor")
    private String nameKorean;

    @ManyToOne
    private Category category;
}