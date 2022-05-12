package com.springservlet.web.frontcontroller.v2.controller;

import com.springservlet.domain.Member;
import com.springservlet.domain.MemberRepository;
import com.springservlet.web.frontcontroller.MyView;
import com.springservlet.web.frontcontroller.v2.ControllerV2;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MemberSaveControllerV2 implements ControllerV2 {

  private final MemberRepository memberRepository = MemberRepository.getInstance();

  @Override
  public MyView process(HttpServletRequest req, HttpServletResponse resp) {
    String username = req.getParameter("username");
    int age = Integer.parseInt(req.getParameter("age"));

    Member member = Member.builder()
      .username(username)
      .age(age)
      .build();
    Member result = memberRepository.save(member);

    req.setAttribute("member", result);
    return MyView.of("/WEB-INF/views/save-result.jsp");
  }
}
