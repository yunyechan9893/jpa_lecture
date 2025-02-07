package jpabook.jpashop.api.v1.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateMemberReponse {

  private Long id;
  private String name;

}
