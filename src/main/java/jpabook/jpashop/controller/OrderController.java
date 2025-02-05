package jpabook.jpashop.controller;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.product.Product;
import jpabook.jpashop.repository.OrderSearch;
import jpabook.jpashop.service.MemberService;
import jpabook.jpashop.service.OrderService;
import jpabook.jpashop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

  private final OrderService orderService;
  private final MemberService memberService;
  private final ProductService productService;

  @GetMapping("/order")
  public String createForm(Model model) {

    List<Member> members = memberService.findMembers();
    List<Product> products = productService.findProducts();

    model.addAttribute("members", members);
    model.addAttribute("items", products);

    return "order/orderForm";
  }

  @PostMapping("/order")
  public String order(
      @RequestParam("memberId") Long memberId,
      @RequestParam("itemId") Long itemId,
      Long count) {

    orderService.order(memberId, itemId, count);

    return "redirect:/orders";
  }

  @GetMapping("/orders")
  public String getOrders(
      @ModelAttribute("orderSearch") OrderSearch orderSearch,
      Model model
  ) {
    List<Order> orders = orderService.findOrders(orderSearch);
    model.addAttribute("orders", orders);

    return "/order/orderList";
  }

  @PostMapping("/orders/{orderId}/cancel")
  public String cancelOrder(
      @PathVariable("orderId") Long orderId
  ) {
    orderService.cancelOrder(orderId);

    return "redirect:/orders";
  }

}
