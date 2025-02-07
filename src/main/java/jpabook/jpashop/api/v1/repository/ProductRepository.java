package jpabook.jpashop.api.v1.repository;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.api.v1.domain.product.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ProductRepository {

  private final EntityManager em;

  public void save(Product product) {
    if (product.getId() == null) {
      em.persist(product);
    } else {
      em.merge(product);
    }
  }

  public Product findById(Long id) {
    return em.find(Product.class, id);
  }

  public List<Product> findAll() {
    return em.createQuery("SELECT p FROM Product p", Product.class)
        .getResultList();
  }

}
