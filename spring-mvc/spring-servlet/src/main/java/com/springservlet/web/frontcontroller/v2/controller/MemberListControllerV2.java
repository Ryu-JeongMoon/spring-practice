package com.springservlet.web.frontcontroller.v2.controller;

import com.springservlet.domain.Member;
import com.springservlet.domain.MemberRepository;
import com.springservlet.web.frontcontroller.MyView;
import com.springservlet.web.frontcontroller.v2.ControllerV2;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MemberListControllerV2 implements ControllerV2 {

  private final MemberRepository memberRepository = MemberRepository.getInstance();

  @Override
  public MyView process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    List<Member> members = memberRepository.findAll();

    req.setAttribute("members", members);

    return MyView.of("/WEB-INF/views/members.jsp");
  }
}
