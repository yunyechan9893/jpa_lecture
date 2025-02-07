package jpabook.jpashop.api.v1.domain;

import jakarta.persistence.Embeddable;
import lombok.*;

@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Embeddable
public class Address {

  private String city;
  private String street;
  private String zipcode;

}