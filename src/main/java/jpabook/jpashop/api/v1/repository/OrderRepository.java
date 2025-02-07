package jpabook.jpashop.api.v1.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jpabook.jpashop.api.v1.constant.OrderStatus;
import jpabook.jpashop.api.v1.domain.Order;
import jpabook.jpashop.api.v1.domain.QOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

import static jpabook.jpashop.api.v1.domain.QDelivery.delivery;
import static jpabook.jpashop.api.v1.domain.QMember.member;
import static jpabook.jpashop.api.v1.domain.QOrderProductGroup.orderProductGroup;
import static jpabook.jpashop.api.v1.domain.product.QProduct.product;

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
        .join(orders.member, member)
        .fetchJoin()
        .join(orders.delivery, delivery)
        .fetchJoin()
        .fetch();
  }

  private BooleanExpression searchName(String name) {
    boolean isNullAndEmpty = Objects.isNull(name) || name.isEmpty();

    return isNullAndEmpty ? null : orders.member.name.eq(name);
  }

  private BooleanExpression searchOrderStatus(OrderStatus orderStatus) {
    return Objects.nonNull(orderStatus) ? orders.orderStatus.eq(orderStatus) : null;
  }

  public List<Order> findAll(int offset, int limit) {

    return queryFactory.selectFrom(orders)
        .distinct()
        .join(orders.member, member)
        .fetchJoin()
        .join(orders.delivery, delivery)
        .fetchJoin()
        .offset(offset)
        .limit(limit)
        .fetch();
 }

}
