package jpabook.jpashop.api.v1.web_controller;

import jakarta.validation.Valid;
import jpabook.jpashop.api.v1.domain.Member;
import jpabook.jpashop.api.v1.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

  private static final String MEMBER_LIST_PAGE = "members/memberList";
  private static final String MEMBER_CREATE_PAGE = "members/createMemberForm";
  private final MemberService memberService;

  @GetMapping("/members")
  public String getMembers(Model model) {
    List<Member> members = memberService.findMembers();
    model.addAttribute("members", members);
    return MEMBER_LIST_PAGE;
  }

  @GetMapping("/members/new")
  public String createForm(Model model) {
    model.addAttribute("memberForm", new MemberForm());

    return MEMBER_CREATE_PAGE;
  }

  @PostMapping("/members/new")
  public String create(@Valid MemberForm model, BindingResult result) {

    if (result.hasErrors()) {
      return MEMBER_CREATE_PAGE;
    }

    Member member = new Member(model.getName(), model.getCity(), model.getStreet(), model.getZipcode());

    memberService.join(member);

    return "redirect:/";
  }

}
