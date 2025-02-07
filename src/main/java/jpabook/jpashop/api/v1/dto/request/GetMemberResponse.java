package jpabook.jpashop.api.v1.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetMemberResponse {

  @NotNull
  @NotEmpty
  Long id;
  String city;
  String street;
  String zipcode;

}
