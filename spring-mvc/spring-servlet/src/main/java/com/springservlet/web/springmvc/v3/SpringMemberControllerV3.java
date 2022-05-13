package com.springservlet.web.springmvc.v3;

import com.springservlet.domain.Member;
import com.springservlet.domain.MemberRepository;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/springmvc/v3/members")
public class SpringMemberControllerV3 {

  private final MemberRepository memberRepository = MemberRepository.getInstance();

  @GetMapping(value = "/new-form")
  public String form() {
    return "new-form";
  }

  @PostMapping("/save")
  public String save(@RequestParam String username, @RequestParam int age, Model model) {
    Member member = Member.builder()
      .username(username)
      .age(age)
      .build();
    Member result = memberRepository.save(member);

    model.addAttribute("member", result);
    return "save-result";
  }

  @GetMapping
  public String list(Model model) throws ServletException, IOException {
    List<Member> members = memberRepository.findAll();

    model.addAttribute("members", members);
    return "members";
  }
}
