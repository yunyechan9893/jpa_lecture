package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

  private final MemberRepository memberRepository;

  @Transactional
  public Long join(Member member) {

    validateDuplicateMember(member);
    memberRepository.save(member);
    return member.getId();
  }

  private void validateDuplicateMember(Member member) {
    Boolean isDuplicated = !memberRepository.findByName(member.getName()).isEmpty();

    if (Boolean.TRUE.equals(isDuplicated)) {
      throw new IllegalStateException("중복회원 입니다.");
    }
  }

  public List<Member> findMembers() {
    return memberRepository.findAll();
  }

  public Member findOne(Long id) {
    return memberRepository.findById(id);
  }

}
