package jpabook.jpashop.domain;

import jakarta.persistence.*;
import jpabook.jpashop.constant.DeliveryStatus;
import jpabook.jpashop.constant.OrderStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Getter
@Setter(AccessLevel.PRIVATE)
@Entity
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@SequenceGenerator(name = "orderSeqGen", sequenceName = "order_seq")
public class Order extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orderSeqGen")
  private Long id;

  @Enumerated(EnumType.STRING)
  private OrderStatus orderStatus;

  private LocalDateTime orderDate;

  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
  private List<OrderProductGroup> orderProductGroups = new ArrayList<>();

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "member_id", foreignKey = @ForeignKey(name = "order_member_fk"))
  private Member member;

  @OneToOne(fetch = LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "delivery_id", foreignKey = @ForeignKey(name = "order_delivery_fk"))
  private Delivery delivery;

  //==연관관계 메서드==//
  public void setMember(Member member) {
    this.member = member;
    member.getOrders().add(this);
  }

  public void addOrderProduct(OrderProductGroup orderProductGroup) {
    this.orderProductGroups.add(orderProductGroup);
    orderProductGroup.updateOrder(this);
  }

  public void setDelivery(Delivery delivery) {
    this.delivery = delivery;
    delivery.updateOrder(this);
  }

  //===생성 메서드=//
  public static Order createOrder(Member member, Delivery delivery, OrderProductGroup... orderProductItems) {
    Order order = new Order();
    order.setMember(member);
    order.setDelivery(delivery);
    for (OrderProductGroup orderProductItem : orderProductItems) {
      order.addOrderProduct(orderProductItem);
    }
    order.setOrderStatus(OrderStatus.ORDER);
    order.setOrderDate(LocalDateTime.now());
    return order;
  }

  //==비지니스 로직==//
  public void cancel() {
    if (delivery.getDeliveryStatus().equals(DeliveryStatus.COMP)) {
      throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다.");
    }

    setOrderStatus(OrderStatus.CANCEL);
    for (OrderProductGroup orderProductGroup : orderProductGroups) {
      orderProductGroup.cancel();
    }
  }

  //==조회 로직==//
  public int getTotalPrice() {
    int totalPrice = 0;

    for (OrderProductGroup orderProductGroup : orderProductGroups) {
      totalPrice += orderProductGroup.getTotalPrice();
    }

    return totalPrice;
  }

}
