package Graduation.CardVisor.repository;

import Graduation.CardVisor.domain.Benefit;
import Graduation.CardVisor.domain.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface BenefitRepository extends JpaRepository<Benefit, Long> {
    public List<Benefit> findAllByCardId(Long id);
}
