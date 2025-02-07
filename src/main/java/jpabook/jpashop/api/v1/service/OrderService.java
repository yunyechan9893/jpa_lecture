package jpabook.jpashop.api.v1.service;

import jpabook.jpashop.api.v1.domain.*;
import jpabook.jpashop.api.v1.domain.product.Product;
import jpabook.jpashop.api.v1.repository.MemberRepository;
import jpabook.jpashop.api.v1.repository.OrderRepository;
import jpabook.jpashop.api.v1.repository.OrderSearch;
import jpabook.jpashop.api.v1.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

  private final OrderRepository orderRepository;
  private final MemberRepository memberRepository;
  private final ProductRepository productRepository;

  @Transactional
  public Long order(Long memberId, Long productId, Long count) {

    Member member = memberRepository.findById(memberId);
    Product product = productRepository.findById(productId);

    Address address = member.getAddress();
    Delivery delivery = Delivery.create(address);

    OrderProductGroup orderProductGroup = OrderProductGroup.createOrderProductGroup(product, product.getPrice(), count);

    Order order = Order.createOrder(member, delivery, orderProductGroup);

    orderRepository.save(order);

    return order.getId();
  }

  @Transactional
  public void cancelOrder(Long orderId) {
    Order order = orderRepository.findOne(orderId);
    order.cancel();
  }

  public List<Order> findOrders(OrderSearch orderSearch) {
    return orderRepository.findAll(orderSearch);
  }

  public List<Order> findPagedOrders(int offset, int limit) {
    return orderRepository.findAll(offset, limit);
  }

}
