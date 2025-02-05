package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@SequenceGenerator(name = "memberSeqGen", sequenceName = "member_seq")
public class Member extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "memberSeqGen")
  private Long id;

  private String name;

  @Embedded
  private Address address;

  @OneToMany(mappedBy = "member")
  List<Order> orders = new ArrayList<>();

  public Member (
      String name,
      String city,
      String street,
      String zipcode
      ) {
    this.name = name;
    this.address = new Address(city, street, zipcode);
  }

}

