package jpabook.jpashop.api.v1.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@SequenceGenerator(name = "teamSeqGen", sequenceName = "team_seq")
public class Team extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "teamSeqGen")
  private Long id;

  private String name;

  @OneToMany(mappedBy = "team")
  private List<Member> members= new ArrayList<>();

  public Team(String name) {
    this.name = name;
  }

  public void addMember(Member member) {
    members.add(member);
  }

}

