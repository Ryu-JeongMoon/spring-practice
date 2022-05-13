package com.springservlet.web.springmvc.v2;

import com.springservlet.domain.Member;
import com.springservlet.domain.MemberRepository;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/springmvc/v2/members")
public class SpringMemberControllerV2 {

  private final MemberRepository memberRepository = MemberRepository.getInstance();

  @RequestMapping(value = "/new-form")
  public ModelAndView form() {
    return new ModelAndView("new-form");
  }

  @RequestMapping("/save")
  public ModelAndView save(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String username = req.getParameter("username");
    int age = Integer.parseInt(req.getParameter("age"));

    Member member = Member.builder()
      .username(username)
      .age(age)
      .build();
    Member result = memberRepository.save(member);

    ModelAndView modelView = new ModelAndView("save-result");
    modelView.addObject("member", result);
    return modelView;
  }

  @RequestMapping
  public ModelAndView list() throws ServletException, IOException {
    List<Member> members = memberRepository.findAll();

    ModelAndView modelView = new ModelAndView("members");
    modelView.addObject("members", members);
    return modelView;
  }
}
