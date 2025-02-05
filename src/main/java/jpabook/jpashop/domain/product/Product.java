package jpabook.jpashop.domain.product;

import jakarta.persistence.*;
import jpabook.jpashop.domain.BaseEntity;
import jpabook.jpashop.domain.Category;
import jpabook.jpashop.domain.OrderProductGroup;
import jpabook.jpashop.exception.NotEnoughStockException;
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

  @ManyToMany(mappedBy = "products")
  private List<Category> categories = new ArrayList<>();

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
