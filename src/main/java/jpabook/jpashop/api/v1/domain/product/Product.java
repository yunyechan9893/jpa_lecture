package jpabook.jpashop.api.v1.domain.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jpabook.jpashop.api.v1.domain.BaseEntity;
import jpabook.jpashop.api.v1.domain.Category;
import jpabook.jpashop.api.v1.domain.OrderProductGroup;
import jpabook.jpashop.api.v1.common.exception.NotEnoughStockException;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter(AccessLevel.PROTECTED)
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@SequenceGenerator(name = "productSeqGen", sequenceName = "product_seq")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn
public abstract class Product extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "productSeqGen")
  private Long id;

  @Column(name = "product_name")
  private String name;

  private Long price;
  private Long stockQuantity;

  @JsonIgnore
  @ManyToMany(mappedBy = "products")
  private List<Category> categories = new ArrayList<>();

  @JsonIgnore
  @OneToMany(mappedBy = "product")
  private List<OrderProductGroup> orderProductGroups = new ArrayList<>();

  //==연관관계 메서드==//
  public void addOrderProductGroup(OrderProductGroup orderProductGroup) {
    orderProductGroups.add(orderProductGroup);
  }

  //==비지니스 로직==//
  public void addStock(Long quantity) {
    this.stockQuantity += quantity;
  }

  public void removeStock(Long quantity) {
    long applyStock = this.stockQuantity - quantity;
    if (applyStock < 0) {
      throw new NotEnoughStockException("재고가 부족합니다.");
    }

    this.stockQuantity = applyStock;
  }

}
