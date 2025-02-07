package jpabook.jpashop.api.v1.repository;

import jpabook.jpashop.api.v1.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
