package Graduation.CardVisor.repository;

import Graduation.CardVisor.domain.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    public List<Favorite> findFavoriteByCard_id(Long id);
}
