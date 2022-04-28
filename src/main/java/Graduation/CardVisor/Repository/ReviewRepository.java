package Graduation.CardVisor.repository;

import Graduation.CardVisor.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
