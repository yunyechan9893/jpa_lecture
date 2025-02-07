package jpabook.jpashop.api.v1.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import jakarta.validation.Valid;
import jpabook.jpashop.api.v1.common.dto.response.ResultResponse;
import jpabook.jpashop.api.v1.dto.request.CreateMemberRequest;
import jpabook.jpashop.api.v1.dto.request.GetMemberResponse;
import jpabook.jpashop.api.v1.dto.request.UpdateMemberRequest;
import jpabook.jpashop.api.v1.dto.response.CreateMemberReponse;
import jpabook.jpashop.api.v1.dto.response.UpdateMemberReponse;
import jpabook.jpashop.api.v1.domain.Member;
import jpabook.jpashop.api.v1.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // @Controller + @ResponseBody
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class MemberApiController {

  private final MemberService memberService;

  @GetMapping
  @Operation(summary = "회원 조회", description = "전체 회원을 조회합니다.")
  public ResultResponse<List<GetMemberResponse>> getMembers() {

    List<Member> members = memberService.findMembers();
    List<GetMemberResponse> responses = members.stream().map(member -> new GetMemberResponse(
        member.getId(),
        member.getAddress().getCity(),
        member.getAddress().getStreet(),
        member.getAddress().getZipcode()
    )).toList();

    return ResultResponse.of(responses).registerMessage("조회 완료");

  }

  @PostMapping
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
      CreateMemberRequest request
  ) {
    Member member = new Member(
        request.getName(),
        request.getAddress().getCity(),
        request.getAddress().getStreet(),
        request.getAddress().getZipcode()
    );

    Long id = memberService.join(member);
    return new CreateMemberReponse(id);
  }

  @PatchMapping("/{memberId}")
  @Operation(summary = "회원 수정", description = "회원 정보를 변경합니다.")
  public UpdateMemberReponse updateMember(
      @PathVariable("memberId") Long memberId,
      @RequestBody @Valid
      @io.swagger.v3.oas.annotations.parameters.RequestBody(
          description = "회원 정보",
          required = true,
          content = @Content(
              mediaType = "application/json",
              examples = @ExampleObject(
                  name = "회원 예제",
                  value = "{\"name\":\"윤예찬찬\"}"
              )
          )
      )
      UpdateMemberRequest request
  ) {
    Long id = memberService.updateMember(memberId, request.getName());
    Member findMember = memberService.findOne(id);
    return new UpdateMemberReponse(findMember.getId(), findMember.getName());
  }

}
