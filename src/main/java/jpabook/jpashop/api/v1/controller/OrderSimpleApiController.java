package jpabook.jpashop.api.v1.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jpabook.jpashop.api.v1.common.dto.response.ResultResponse;
import jpabook.jpashop.api.v1.domain.Order;
import jpabook.jpashop.api.v1.dto.request.GetOrder;
import jpabook.jpashop.api.v1.dto.request.GetOrderResponse;
import jpabook.jpashop.api.v1.repository.OrderSearch;
import jpabook.jpashop.api.v1.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/simple-orders")
public class OrderSimpleApiController {

  private final OrderService orderService;

  @GetMapping
  @Transactional(readOnly = true)
  public ResultResponse<List<GetOrderResponse>> getOrders() {
    List<Order> orders = orderService.findOrders(new OrderSearch());

    List<GetOrderResponse> response = orders.stream().map(order ->
        new GetOrderResponse(
            order.getId(),
            order.getMember().getName(),
            order.getOrderDate(),
            order.getOrderStatus(),
            order.getDelivery().getAddress())).toList();

    return ResultResponse.of(response).registerMessage("상품 조회 완료");
  }

  @GetMapping("/all")
  @Operation(summary = "페이징된 주문 조회", description = "주문을 조회합니다.")
  public ResultResponse<List<GetOrder>> getAllOrders(
      @Parameter(description = "페이지 오프셋 (0부터 시작)", example = "0")
      @RequestParam(value = "offset", defaultValue = "0") int offset,

      @Parameter(description = "가져올 주문 수", example = "100")
      @RequestParam(value = "limit", defaultValue = "100") int limit
  ) {
    List<Order> orders = orderService.findPagedOrders(offset, limit);

    List<GetOrder> orderDtos = orders.stream().map(GetOrder::new).toList();

    return ResultResponse.of(orderDtos).registerMessage("상품 조회 완료");
  }

}
