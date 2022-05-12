package com.springservlet.web.frontcontroller.v1.controller;

import com.springservlet.domain.Member;
import com.springservlet.domain.MemberRepository;
import com.springservlet.web.frontcontroller.v1.ControllerV1;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MemberSaveControllerV1 implements ControllerV1 {

  private final MemberRepository memberRepository = MemberRepository.getInstance();

  @Override
  public void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String username = req.getParameter("username");
    int age = Integer.parseInt(req.getParameter("age"));

    Member member = Member.builder()
      .username(username)
      .age(age)
      .build();
    Member result = memberRepository.save(member);

    req.setAttribute("member", result);

    String viewPath = "/WEB-INF/views/save-result.jsp";
    RequestDispatcher dispatcher = req.getRequestDispatcher(viewPath);
    dispatcher.forward(req, resp);
  }
}
