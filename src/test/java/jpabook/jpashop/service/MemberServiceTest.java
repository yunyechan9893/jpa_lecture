package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class MemberServiceTest {

  @Autowired
  MemberRepository memberRepository;

  @Autowired
  MemberService memberService;

  @Test
  void 회원가입() {
    //given
    Member member = new Member("memberA", "", "", "");

    //when
    Long joinId = memberService.join(member);

    //then
    Assertions.assertThat(member).isEqualTo(memberRepository.findById(joinId));
  }

  @Test
  void 중복_회원_예외() {
    //given
    Member member1 = new Member("memberA", "", "", "");
    Member member2 = new Member("memberA", "", "", "");

    //when
    memberService.join(member1);

    try {
      memberService.join(member2);
    } catch (IllegalStateException e) {
      return;
    }

    //then
    Assertions.fail("예외가 발생해야 한다.");
  }

}