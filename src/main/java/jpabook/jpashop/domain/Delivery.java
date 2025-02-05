package jpabook.jpashop.domain;

import jakarta.persistence.*;
import jpabook.jpashop.constant.DeliveryStatus;
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

  @OneToOne(mappedBy = "delivery")
  @JoinColumn(name = "order_id", foreignKey = @ForeignKey(name = "delivery__order__fk"))
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