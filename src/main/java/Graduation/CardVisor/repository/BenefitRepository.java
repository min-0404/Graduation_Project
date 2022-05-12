package Graduation.CardVisor.repository;

import Graduation.CardVisor.domain.benefit.Benefit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BenefitRepository extends JpaRepository<Benefit, Long> {
    public List<Benefit> findAllByCardId(Long id);
}
