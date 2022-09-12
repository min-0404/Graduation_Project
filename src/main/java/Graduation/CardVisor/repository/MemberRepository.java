package Graduation.CardVisor.repository;

import Graduation.CardVisor.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    public Member findByNickname(String username);
    public boolean existsByNickname(String username);

}
