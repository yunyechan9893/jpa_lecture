package jpabook.jpashop.api.v1.repository;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.api.v1.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl {

  private final EntityManager em;

  public void save(Member member) {
    em.persist(member);
  }

  public Member findById(Long id) {
    return em.find(Member.class, id);
  }

  public List<Member> findAll() {
    return em.createQuery("SELECT m FROM Member AS m", Member.class)
        .getResultList();
  }

  public List<Member> findByName(String name) {
    return em.createQuery("SELECT m FROM Member AS m WHERE m.name = :name", Member.class)
        .setParameter("name", name)
        .getResultList();
  }

}
