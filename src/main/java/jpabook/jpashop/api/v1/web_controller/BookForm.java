package jpabook.jpashop.api.v1.web_controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookForm {

  private Long id;

  private String name;
  private Long price;
  private Long stockQuantity;

  private String author;
  private String isbn;

}
