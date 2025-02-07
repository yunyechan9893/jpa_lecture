package jpabook.jpashop.api.v1.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetOrderProductGroup {

  private String itemName;//상품 명
  private Long orderPrice; //주문 가격
  private Long count;      //주문 수량

}
