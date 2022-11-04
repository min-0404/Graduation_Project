package Graduation.CardVisor.repository;

import Graduation.CardVisor.domain.Category;
import Graduation.CardVisor.domain.member.Member;
import Graduation.CardVisor.domain.servicetwo.ServiceTwo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServiceTwoRepository extends JpaRepository<ServiceTwo, Long> {

    public ServiceTwo findByMemberAndCategory(Member member, Category category);
    public List<ServiceTwo> findByMemberId(Long memberId);
}
