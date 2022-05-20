package Graduation.CardVisor.repository;

import Graduation.CardVisor.domain.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {

    public Card findCardById(Long id);
    public List<Card> findAll();
    public List<Card> findAllById(Long id);
    public List<Card> findAllByCompany(String company);
    public List<Card> findAllByLink(String link);
    public List<Card> findAllByName(String name);
    public Card findCardByCompany(String company);
    public Card findCardByType(String type);
    public Card findCardByCompany(Long id);
    public Card deleteAllById(Long id);
}
