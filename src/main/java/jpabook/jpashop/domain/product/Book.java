package jpabook.jpashop.domain.product;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jpabook.jpashop.domain.Category;
import lombok.*;

import java.util.List;

@Getter
@Setter(AccessLevel.PRIVATE)
@Entity
@DiscriminatorValue("B")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Book extends Product {

  private String author;
  private String isbn;

  public static Book create(
      String name,
      Long price,
      Long stockQuantity,
      List<Category> categories,
      String author,
      String isbn
  ) {
    Book book = new Book();
    book.setName(name);
    book.setPrice(price);
    book.setStockQuantity(stockQuantity);
    book.setCategories(categories);
    book.setAuthor(author);
    book.setIsbn(isbn);

    return book;
  }

  public static Book update(
      Long id,
      String name,
      Long price,
      Long stockQuantity,
      List<Category> categories,
      String author,
      String isbn
  ) {
    Book book = new Book();
    book.setId(id);
    book.setName(name);
    book.setPrice(price);
    book.setStockQuantity(stockQuantity);
    book.setCategories(categories);
    book.setAuthor(author);
    book.setIsbn(isbn);

    return book;
  }

}
