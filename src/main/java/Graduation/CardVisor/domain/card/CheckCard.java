package Graduation.CardVisor.domain.card;

import lombok.Data;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Data
@DiscriminatorValue("check")
public class CheckCard extends Card{
}
