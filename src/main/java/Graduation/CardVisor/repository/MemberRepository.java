package Graduation.CardVisor.repository;

import Graduation.CardVisor.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByNickname(String username);
    public boolean existsByNickname(String username);

    public Member getMemberById(Long memberId);

    Member findMemberById(Long id);
}
