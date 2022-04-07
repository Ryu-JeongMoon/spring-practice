package org.example.shop.controller;

import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.shop.domain.Address;
import org.example.shop.domain.Member;
import org.example.shop.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MemberController {

  private final MemberService memberService;

  @GetMapping("/members/new")
  public String createForm(Model model) {
    model.addAttribute("memberForm", new MemberForm());
    return "members/createMemberForm";
  }

  @PostMapping("/members/new")
  public String create(@Valid MemberForm form, BindingResult bindingResult) {

    if (bindingResult.hasErrors()) {
      log.info("Error = {}", bindingResult);
      return "members/createMemberForm";
    }

    Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());

    Member member = Member.builder()
      .name(form.getName())
      .address(address)
      .build();

    memberService.join(member);
    return "redirect:/";
  }

  @GetMapping("/members")
  public String list(Model model) {
    List<Member> members = memberService.findMembers();
    model.addAttribute("members", members);
    return "members/memberList";
  }
}
