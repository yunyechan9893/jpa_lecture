package jpabook.jpashop.service;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jpabook.jpashop.api.v1.domain.Member;
import jpabook.jpashop.api.v1.domain.Team;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static jpabook.jpashop.api.v1.domain.QMember.member;
import static jpabook.jpashop.api.v1.domain.QTeam.team;
import static org.assertj.core.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class MemberQueryDslTest {

  EntityManager entityManager;
  JPAQueryFactory jpaQueryFactory;

  @Autowired
  public MemberQueryDslTest(EntityManager entityManager) {
    this.entityManager = entityManager;
    this.jpaQueryFactory = new JPAQueryFactory(entityManager);
  }
  
  @BeforeEach
  void  before() {
    createMember("member1", "1", "1", "1");
    createMember("member2", "2", "2", "2");
    createMember("member3", "3", "3", "3");
    createMember("member4", "4", "4", "4");
    createMember("member5", "5", "5", "5");
    createMember("member6", "6", "6", "6");
  }

  private void createMember(String name, String city, String street, String zipcode) {
    Member member1 = new Member(name, city, street, zipcode);
    entityManager.persist(member1);
  }

  @Test
  void 쿼리_DSL_페이징_테스트() {
    QueryResults<Member> memberQueryResults = jpaQueryFactory.selectFrom(member)
        .fetchResults();

    long total = memberQueryResults.getTotal();
    List<Member> results = memberQueryResults.getResults();

    System.out.println("total : " + total);
    results.forEach(System.out::println);
  }

  @Test
  void 쿼리_DSL_정렬_테스트() {
    List<Member> memberQueryResults = jpaQueryFactory.selectFrom(member)
        .orderBy(member.id.desc(), member.name.desc().nullsFirst())
        .fetch();


    memberQueryResults.forEach(System.out::println);
  }

  @Test
  void 쿼리_DSL_패치_결과_테스트() {
    QueryResults<Member> memberQueryResults = jpaQueryFactory.selectFrom(member)
        .where(member.id.goe(3L))
        .fetchResults();

    long total = memberQueryResults.getTotal();

    assertThat(total).isEqualTo(6L);
    memberQueryResults.getResults().forEach(System.out::println);
  }

  @Test
  void 쿼리_DSL_세타조인() {
    createTeam("teamA");
    createTeam("teamB");
    createMember("teamA", "", "", "");
    createMember("teamB", "", "", "");

      //when
    List<Member> members = jpaQueryFactory.select(member)
        .from(member, team)
        .where(member.name.eq(team.name))
        .fetch();

    //then
    assertThat(members).hasSize(2);
    members.forEach(System.out::println);
  }

  private void createTeam(String name) {
    Team team = new Team(name);
    entityManager.persist(team);
  }

}