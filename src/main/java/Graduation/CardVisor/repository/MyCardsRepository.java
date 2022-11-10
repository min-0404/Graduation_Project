package Graduation.CardVisor.repository;

import Graduation.CardVisor.domain.member.Member;
import Graduation.CardVisor.domain.mycards.MyCards;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface MyCardsRepository extends JpaRepository<MyCards, Long> {

    public List<MyCards> getByMemberId(Long id);

    @Transactional
    public void deleteAllByMemberId(Long memberId);
}
