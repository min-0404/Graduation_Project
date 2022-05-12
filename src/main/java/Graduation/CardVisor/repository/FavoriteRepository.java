package Graduation.CardVisor.repository;

import Graduation.CardVisor.domain.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    public List<Favorite> findFavoriteByCard_id(Long id);
}
