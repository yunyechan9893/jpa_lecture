package jpabook.jpashop.repository;

import jpabook.jpashop.constant.OrderStatus;
import lombok.Data;

@Data
public class OrderSearch {

  private String memberName;
  private OrderStatus orderStatus;

}
