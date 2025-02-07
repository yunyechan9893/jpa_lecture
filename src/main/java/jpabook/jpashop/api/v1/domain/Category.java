package jpabook.jpashop.api.v1.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jpabook.jpashop.api.v1.domain.product.Product;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Category {

  @Id
  @GeneratedValue
  private Long id;

  private String name;

  @ManyToMany
  @JoinTable(name = "category_product_group",
      joinColumns = @JoinColumn(name = "category_id"),
      inverseJoinColumns = @JoinColumn(name = "product_id"))
  private List<Product> products = new ArrayList<>();

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "parent_id", foreignKey = @ForeignKey(name = "category__category__fk"))
  private Category parent;

  @JsonIgnore
  @OneToMany(mappedBy = "parent")
  private List<Category> childes = new ArrayList<>();

  private void setParent(Category parent) {
    this.parent = parent;
  }

  //==연관관계 메서드==//
  public void addChildCategory(Category child) {
    this.childes.add(child);
    child.setParent(this);
  }

}
