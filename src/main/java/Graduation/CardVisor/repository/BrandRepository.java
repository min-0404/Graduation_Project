package Graduation.CardVisor.repository;

import Graduation.CardVisor.domain.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<Brand, Long> {

    public Brand getByNameEngish(String name);
}
