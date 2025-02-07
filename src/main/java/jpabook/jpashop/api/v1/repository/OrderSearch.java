package jpabook.jpashop.api.v1.repository;

import jpabook.jpashop.api.v1.constant.OrderStatus;
import lombok.Data;

@Data
public class OrderSearch {

  private String memberName;
  private OrderStatus orderStatus;

}
