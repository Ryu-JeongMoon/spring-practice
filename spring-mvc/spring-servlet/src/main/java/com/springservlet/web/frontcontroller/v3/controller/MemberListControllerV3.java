package com.springservlet.web.frontcontroller.v3.controller;

import com.springservlet.domain.Member;
import com.springservlet.domain.MemberRepository;
import com.springservlet.web.frontcontroller.ModelView;
import com.springservlet.web.frontcontroller.v3.ControllerV3;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;

public class MemberListControllerV3 implements ControllerV3 {

  private final MemberRepository memberRepository = MemberRepository.getInstance();

  @Override
  public ModelView process(Map<String, String> params) throws ServletException, IOException {
    List<Member> members = memberRepository.findAll();

    ModelView modelView = ModelView.of("members");
    modelView.addAttribute("members", members);
    return modelView;
  }
}
