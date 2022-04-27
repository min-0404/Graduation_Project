package Graduation.CardVisor.repository;

import Graduation.CardVisor.domain.Card;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class CardRepository {
    private final EntityManager em;

    public Card find(Long id){
        return em.find(Card.class, id);
    }

}
