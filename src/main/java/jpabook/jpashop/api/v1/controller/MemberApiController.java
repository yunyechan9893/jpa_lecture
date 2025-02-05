package jpabook.jpashop.api.v1.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import jakarta.validation.Valid;
import jpabook.jpashop.api.v1.dto.response.CreateMemberReponse;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController // @Controller + @ResponseBody
@RequiredArgsConstructor
public class MemberApiController {

  private final MemberService memberService;


  @PostMapping("/api/v1/members")
  @Operation(summary = "회원 가입", description = "새로운 회원을 등록합니다.")
  public CreateMemberReponse saveMember(
      @RequestBody @Valid
      @io.swagger.v3.oas.annotations.parameters.RequestBody(
          description = "회원 정보",
          required = true,
          content = @Content(
              mediaType = "application/json",
              examples = @ExampleObject(
                  name = "회원 예제",
                  value = "{\"name\": \"윤예찬\", \n\"address\":{\n\"city\": \"뉴욕\", \n\"street\": \"뉴욕\", \n\"zipcode\": \"뉴욕\"}}"
              )
          )
      )
      Member member
  ) {
    Long id = memberService.join(member);
    return new CreateMemberReponse(id);
  }

}
