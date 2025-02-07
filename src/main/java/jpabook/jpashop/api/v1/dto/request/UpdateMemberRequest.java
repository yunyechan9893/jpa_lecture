package jpabook.jpashop.api.v1.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateMemberRequest {

  @NotNull
  @NotEmpty
  private String name;

}
