package jpabook.jpashop.api.v1.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


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

  @JsonIgnore
  @OneToMany(mappedBy = "member")
  List<Order> orders = new ArrayList<>();

  //==생성자 메서드==//
  public Member (
      String name,
      String city,
      String street,
      String zipcode
      ) {
    this.name = name;
    this.address = new Address(city, street, zipcode);
  }

  //==수정 메서드==//
  public void updateMember(String name) {
    this.name = name;
  }

  //==로직==//
  public Address getAddress() {
    if (Objects.isNull(this.address)) {
      return new Address();
    }

    return this.address;
  }

}

