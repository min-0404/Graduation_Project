package Graduation.CardVisor.domain;

import lombok.Data;

import javax.persistence.*;

@Entity(name = "brand")
@Data
public class Brand {

    @Id
    @Column(name = "brand_id")
    private Long id;

    @Column(name = "name_eng")
    private String nameEngish;

    @Column(name = "name_kor")
    private String nameKorean;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;
}