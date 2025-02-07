package jpabook.jpashop.api.v1.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jpabook.jpashop.api.v1.constant.DeliveryStatus;
import lombok.*;

@Getter
@Setter(AccessLevel.PRIVATE)
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@SequenceGenerator(name = "deliverySeqGen", sequenceName = "delivery_seq")
public class Delivery extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "deliverySeqGen")
  private Long id;

  @Embedded
  private Address address;

  @Enumerated(EnumType.STRING)
  private DeliveryStatus deliveryStatus;

  @JsonIgnore
  @OneToOne(mappedBy = "delivery")
  private Order order;

  public void updateOrder(Order order) {
    this.order = order;
  }

  public static Delivery create(Address address) {
    Delivery delivery = new Delivery();
    delivery.setAddress(address);
    delivery.setDeliveryStatus(DeliveryStatus.BEFORE_PREPARATION);

    return delivery;
  }

}