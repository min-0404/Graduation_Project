package Graduation.CardVisor.repository;

import Graduation.CardVisor.domain.Card;
import Graduation.CardVisor.domain.Fee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FeeRepository extends JpaRepository<Fee, Long> {

    public Fee getFeeByCardIs(Card card);

}
