package jpabook.jpashop.api.v1.common.exclusion;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jpabook.jpashop.api.v1.domain.*;
import jpabook.jpashop.api.v1.domain.product.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class InitDatabase {

  public final InitService initService;

  @PostConstruct
  public void init() {
    initService.init();
    initService.init2();
  }

  @Component
  @Transactional
  @RequiredArgsConstructor
  static class InitService {

    private final EntityManager em;

    public void init() {
      Member member1 = createMember("userA", "서울", "1", "1111");

      Book book1 = createBook("JPA1 BOOK", 10000L, 100L, "", "");
      Book book2 = createBook("JPA2 BOOK", 20000L, 100L, "", "");

      OrderProductGroup orderItem1 = createOrderItem(book1, book1.getPrice(), 1L);
      OrderProductGroup orderItem2 = createOrderItem(book2, book2.getPrice(), 2L);

      Delivery delivery = createDelivery(member1);
      createOrder(member1, delivery, orderItem1, orderItem2);
    }

    public void init2() {

      Member member1 = createMember("userB", "인천", "2", "2222");

      Book book1 = createBook("SPRINT1 BOOK", 20000L, 200L, "", "");
      Book book2 = createBook("SPRINT2 BOOK", 40000L, 300L, "", "");

      OrderProductGroup orderItem1 = createOrderItem(book1, book1.getPrice(), 3L);
      OrderProductGroup orderItem2 = createOrderItem(book2, book2.getPrice(), 4L);

      Delivery delivery = createDelivery(member1);
      createOrder(member1, delivery, orderItem1, orderItem2);
    }

    private Member createMember(String name, String city, String street, String zipcode) {
      Member member = new Member(name, city, street, zipcode);
      em.persist(member);

      return member;
    }

    private Book createBook(String name, Long price, Long stockQuantity, String author, String isbn) {
      Book book = Book.create(name, price, stockQuantity, List.of(), author, isbn);
      em.persist(book);

      return book;
    }

    private static OrderProductGroup createOrderItem(Book book, Long price, Long count) {
      return OrderProductGroup.createOrderProductGroup(book, price, count);
    }

    private static Delivery createDelivery(Member member1) {
      return Delivery.create(member1.getAddress());
    }

    private void createOrder(Member member1, Delivery delivery, OrderProductGroup orderItem1, OrderProductGroup orderItem2) {
      Order order = Order.createOrder(member1, delivery, orderItem1, orderItem2);
      em.persist(order);
    }

  }
}
