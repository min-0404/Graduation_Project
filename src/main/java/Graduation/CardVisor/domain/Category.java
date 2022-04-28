package Graduation.CardVisor.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "category")
@Data
public class Category {

    @Id
    @Column(name = "category_id")
    private Long id;

    @Column(name = "category_name")
    private String name;
}