package com.springservlet.web.springmvc.v1;

import com.springservlet.domain.Member;
import com.springservlet.domain.MemberRepository;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SpringMemberSaveControllerV1 {

  private final MemberRepository memberRepository = MemberRepository.getInstance();

  @RequestMapping("/springmvc/v1/members/save")
  public ModelAndView process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
}
