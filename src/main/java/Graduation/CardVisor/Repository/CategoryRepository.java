package Graduation.CardVisor.repository;

import Graduation.CardVisor.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
