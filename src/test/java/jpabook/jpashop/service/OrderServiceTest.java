package jpabook.jpashop.service;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.constant.OrderStatus;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.product.Book;
import jpabook.jpashop.domain.product.Product;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class OrderServiceTest {

  @Autowired
  EntityManager em;

  @Autowired
  OrderService orderService;

  @Autowired
  OrderRepository orderRepository;

  @Test
  void 상품주문() {
    //given
    Member member = createMember();
    Product product = createProduct();

    Long orderCount = 2L;

    //when
    Long orderId = orderService.order(member.getId(), product.getId(), orderCount);

    //then
    Order order = orderRepository.findOne(orderId);

    assertThat(order.getOrderStatus())
        .isEqualTo(OrderStatus.ORDER);

    assertThat(order.getOrderProductGroups().size())
        .isEqualTo(1L);

    assertThat(order.getTotalPrice())
        .isEqualTo(10_000L * 2);
  }

  private Product createProduct() {
    Product product = Book.create("수학책", 10_000L, 3L, List.of(), "없음", "없음");
    em.persist(product);
    return product;
  }

  private Member createMember() {
    Member member = new Member("예찬", "뉴욕", "", "");
    em.persist(member);
    return member;
  }

  @Test
  void 상품주문_재고수량초과() {
    //given
    Member member = createMember();
    Product product = createProduct();

    Long expectedOrderCount = 4L;

    //when
    assertThatThrownBy(() -> orderService.order(member.getId(), product.getId(), expectedOrderCount))
        .isInstanceOf(NotEnoughStockException.class)
        .hasMessageContaining("재고가 부족합니다");
  }

  @Test
  void 주문취소() {
    //given
    Member member = createMember();
    Product product = createProduct();

    Long orderCount = 2L;

    Long orderId = orderService.order(member.getId(), product.getId(), orderCount);

    //when
    orderService.cancelOrder(orderId);

    //then
    Order order = orderRepository.findOne(orderId);
    assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.CANCEL);
    assertThat(product.getStockQuantity()).isEqualTo(3L);
  }


}