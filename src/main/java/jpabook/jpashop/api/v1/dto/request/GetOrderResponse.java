package jpabook.jpashop.api.v1.dto.request;

import jpabook.jpashop.api.v1.constant.OrderStatus;
import jpabook.jpashop.api.v1.domain.Address;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class GetOrderResponse {

  private Long orderId;
  private String name;
  private LocalDateTime orderDate;
  private OrderStatus orderStatus;
  private Address address;

}
