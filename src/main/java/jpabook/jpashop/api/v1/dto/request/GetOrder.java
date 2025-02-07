package jpabook.jpashop.api.v1.dto.request;

import jpabook.jpashop.api.v1.constant.OrderStatus;
import jpabook.jpashop.api.v1.domain.Address;
import jpabook.jpashop.api.v1.domain.Order;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Getter
@Setter
public class GetOrder {
  private Long orderId;
  private String name;
  private LocalDateTime orderDate; //주문시간
  private OrderStatus orderStatus;
  private Address address;
  private List<GetOrderProductGroup> orderItems;

  public GetOrder(Order order) {
    orderId = order.getId();
    name = order.getMember().getName();
    orderDate = order.getOrderDate();
    orderStatus = order.getOrderStatus();
    address = order.getDelivery().getAddress();
    orderItems = order.getOrderProductGroups().stream()
        .map(product -> new GetOrderProductGroup(product.getProduct().getName(), product.getPrice(), product.getCount()))
        .collect(toList());
  }

}
