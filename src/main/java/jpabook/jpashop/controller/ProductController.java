package jpabook.jpashop.controller;

import jpabook.jpashop.domain.product.Book;
import jpabook.jpashop.domain.product.Product;
import jpabook.jpashop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProductController {

  private static final String PRODUCT_LIST_PAGE = "items/itemList";
  private static final String PRODUCT_CREATE_PAGE = "items/createItemForm";
  private static final String PRODUCT_UPDATE_PAGE = "items/updateItemForm";
  private final ProductService productService;

  @GetMapping("/items/new")
  public String createForm(Model model) {
    model.addAttribute("form", new BookForm());

    return PRODUCT_CREATE_PAGE;
  }

  @PostMapping("/items/new")
  public String create(BookForm form) {
    Book book = Book.create(
        form.getName(),
        form.getPrice(),
        form.getStockQuantity(),
        List.of(),
        form.getAuthor(),
        form.getIsbn()
    );

    productService.saveProduct(book);

    return "redirect:/items";
  }

  @GetMapping("/items")
  public String getProducts(Model model) {
    List<Product> products = productService.findProducts();
    model.addAttribute("items",products);

    return PRODUCT_LIST_PAGE;
  }

  @GetMapping("/items/{itemId}/edit")
  public String updateItemForm(@PathVariable("itemId") Long itemId, Model model) {
    Book book = (Book) productService.findOne(itemId);

    BookForm bookForm = new BookForm(
        book.getId(),
        book.getName(),
        book.getPrice(),
        book.getStockQuantity(),
        book.getAuthor(),
        book.getIsbn()
    );

    model.addAttribute("form", bookForm);

    return PRODUCT_UPDATE_PAGE;
  }

  @PostMapping("/items/{itemId}/edit")
  public String updateProduct(@PathVariable("itemId") Long itemId, @ModelAttribute("form") BookForm bookForm) {

    Book book = Book.update(
        bookForm.getId(),
        bookForm.getName(),
        bookForm.getPrice(),
        bookForm.getStockQuantity(),
        List.of(),
        bookForm.getAuthor(),
        bookForm.getIsbn()
    );

    productService.saveProduct(book);

    return "redirect:/items";
  }

}
