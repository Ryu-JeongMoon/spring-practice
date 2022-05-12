package com.springservlet.web.frontcontroller.v3.controller;

import com.springservlet.domain.Member;
import com.springservlet.domain.MemberRepository;
import com.springservlet.web.frontcontroller.ModelView;
import com.springservlet.web.frontcontroller.v3.ControllerV3;
import java.io.IOException;
import java.util.Map;
import javax.servlet.ServletException;

public class MemberSaveControllerV3 implements ControllerV3 {

  private final MemberRepository memberRepository = MemberRepository.getInstance();

  @Override
  public ModelView process(Map<String, String> params) throws ServletException, IOException {
    String username = params.get("username");
    int age = Integer.parseInt(params.get("age"));

    Member member = Member.builder()
      .username(username)
      .age(age)
      .build();
    Member result = memberRepository.save(member);

    ModelView modelView = ModelView.of("save-result");
    modelView.addAttribute("member", result);
    return modelView;
  }
}
