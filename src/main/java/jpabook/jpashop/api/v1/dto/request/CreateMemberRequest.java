package jpabook.jpashop.api.v1.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jpabook.jpashop.api.v1.domain.Address;
import lombok.Data;

@Data
public class CreateMemberRequest {

  @NotNull
  @NotEmpty
  private String name;
  private Address address;

}
