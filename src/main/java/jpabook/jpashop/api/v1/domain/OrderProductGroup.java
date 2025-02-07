package jpabook.jpashop.api.v1.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jpabook.jpashop.api.v1.domain.product.Product;
import lombok.*;

import static jakarta.persistence.FetchType.LAZY;

@Getter
@Setter(AccessLevel.PRIVATE)
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderProductGroup {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Long price;

  private Long count;

  @JsonIgnore
  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "order_id", foreignKey = @ForeignKey(name = "opg__order__fk"))
  private Order order;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "product_id", foreignKey = @ForeignKey(name = "opg__product__fk"))
  private Product product;

  public void updateOrder(Order order) {
    this.order = order;
  }

  //==생성 메서드==//
  public static OrderProductGroup createOrderProductGroup(Product product, Long orderPrice, Long count) {
    OrderProductGroup orderProductGroup = new OrderProductGroup();
    orderProductGroup.setProduct(product);
    orderProductGroup.setPrice(orderPrice);
    orderProductGroup.setCount(count);

    product.removeStock(count);
    return orderProductGroup;
  }

  //==비지니스 로직==//
  public void cancel() {
    getProduct().addStock(count);
  }

  //==조회 로직==//
  public Long getTotalPrice() {
    return getPrice() * getCount();
  }


}
