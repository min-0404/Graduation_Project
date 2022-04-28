package Graduation.CardVisor.repository;

import Graduation.CardVisor.domain.Benefit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BenefitRepository extends JpaRepository<Benefit, Long> {
    public List<Benefit> findBenefitByCard_Id(Long id);
}
