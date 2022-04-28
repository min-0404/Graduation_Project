package Graduation.CardVisor.repository;

import Graduation.CardVisor.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
