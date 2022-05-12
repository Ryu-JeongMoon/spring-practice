package com.springservlet.web.frontcontroller.v1.controller;

import com.springservlet.domain.Member;
import com.springservlet.domain.MemberRepository;
import com.springservlet.web.frontcontroller.v1.ControllerV1;
import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MemberListControllerV1 implements ControllerV1 {

  private final MemberRepository memberRepository = MemberRepository.getInstance();

  @Override
  public void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    List<Member> members = memberRepository.findAll();

    req.setAttribute("members", members);

    String viewPath = "/WEB-INF/views/members.jsp";
    RequestDispatcher dispatcher = req.getRequestDispatcher(viewPath);
    dispatcher.forward(req, resp);
  }
}
