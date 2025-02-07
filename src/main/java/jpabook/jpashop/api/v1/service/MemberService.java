package jpabook.jpashop.api.v1.service;

import jpabook.jpashop.api.v1.domain.Member;
import jpabook.jpashop.api.v1.repository.MemberRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

  private final MemberRepositoryImpl memberRepositoryImpl;

  @Transactional
  public Long join(Member member) {

    validateDuplicateMember(member);
    memberRepositoryImpl.save(member);
    return member.getId();
  }

  private void validateDuplicateMember(Member member) {
    Boolean isDuplicated = !memberRepositoryImpl.findByName(member.getName()).isEmpty();

    if (Boolean.TRUE.equals(isDuplicated)) {
      throw new IllegalStateException("중복회원 입니다.");
    }
  }

  @Transactional
  public Long updateMember(Long memberId, String name) {
    Member member = memberRepositoryImpl.findById(memberId);

    if (Objects.isNull(member)) {
      throw new IllegalStateException("회원이 없습니다.");
    }

    member.updateMember(name);

    return memberId;
  }

  public List<Member> findMembers() {
    return memberRepositoryImpl.findAll();
  }

  public Member findOne(Long id) {
    return memberRepositoryImpl.findById(id);
  }

}
