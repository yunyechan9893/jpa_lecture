package jpabook.jpashop.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jpabook.jpashop.constant.OrderStatus;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.QOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

  private static final QOrder orders = QOrder.order;
  private final JPAQueryFactory queryFactory;
  private final EntityManager em;

  public void save(Order order) {
    em.persist(order);
  }

  public Order findOne(Long id) {
    return em.find(Order.class, id);
  }

  public List<Order> findAll(OrderSearch orderSearch) {

    return queryFactory.selectFrom(orders)
        .where(searchName(orderSearch.getMemberName()),
            searchOrderStatus(orderSearch.getOrderStatus()))
        .fetch();
  }

  private BooleanExpression searchName(String name) {
    boolean isNullAndEmpty = Objects.isNull(name) || name.isEmpty();

    return isNullAndEmpty ? null : orders.member.name.eq(name);
  }

  private BooleanExpression searchOrderStatus(OrderStatus orderStatus) {
    return Objects.nonNull(orderStatus) ? orders.orderStatus.eq(orderStatus) : null;
  }

}
