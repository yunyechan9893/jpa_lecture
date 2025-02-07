package jpabook.jpashop.api.v1.service;

import jpabook.jpashop.api.v1.domain.product.Product;
import jpabook.jpashop.api.v1.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductService {

  private final ProductRepository productRepository;

  @Transactional
  public void saveProduct(Product product) {
    productRepository.save(product);
  }

  public List<Product> findProducts() {
    return productRepository.findAll();
  }

  public Product findOne(Long id) {
    return productRepository.findById(id);
  }

}
