package com.springservlet.web.springmvc.v1;

import com.springservlet.domain.Member;
import com.springservlet.domain.MemberRepository;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SpringMemberListControllerV1 {

  private final MemberRepository memberRepository = MemberRepository.getInstance();

  @RequestMapping("/springmvc/v1/members")
  public ModelAndView process() throws ServletException, IOException {
    List<Member> members = memberRepository.findAll();

    ModelAndView modelView = new ModelAndView("members");
    modelView.addObject("members", members);
    return modelView;
  }
}
