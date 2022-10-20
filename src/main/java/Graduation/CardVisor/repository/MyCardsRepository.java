package Graduation.CardVisor.repository;

import Graduation.CardVisor.domain.MyCards;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MyCardsRepository extends JpaRepository<MyCards, Long> {
}
