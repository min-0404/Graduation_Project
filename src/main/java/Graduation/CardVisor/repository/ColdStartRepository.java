package Graduation.CardVisor.repository;

import Graduation.CardVisor.domain.ColdStart;
import Graduation.CardVisor.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ColdStartRepository extends JpaRepository<ColdStart, Long> {

    public List<ColdStart> findByMemberId(Long id);

    public ColdStart findByMemberIdAndRank(Long id, int rank);

    @Transactional
    public void deleteAllByMemberId(Long memberId);
}
