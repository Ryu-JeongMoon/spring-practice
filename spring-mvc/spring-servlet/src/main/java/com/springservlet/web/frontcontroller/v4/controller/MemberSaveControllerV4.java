package com.springservlet.web.frontcontroller.v4.controller;

import com.springservlet.domain.Member;
import com.springservlet.domain.MemberRepository;
import com.springservlet.web.frontcontroller.v4.ControllerV4;
import java.io.IOException;
import java.util.Map;
import javax.servlet.ServletException;

public class MemberSaveControllerV4 implements ControllerV4 {

  private final MemberRepository memberRepository = MemberRepository.getInstance();

  @Override
  public String process(Map<String, String> params, Map<String, Object> models) throws ServletException, IOException {
    String username = params.get("username");
    int age = Integer.parseInt(params.get("age"));

    Member member = Member.builder()
      .username(username)
      .age(age)
      .build();
    Member result = memberRepository.save(member);

    models.put("member", result);
    return "save-result";
  }
}
