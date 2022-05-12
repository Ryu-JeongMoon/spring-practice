package com.springservlet.web.frontcontroller.v4.controller;

import com.springservlet.domain.MemberRepository;
import com.springservlet.web.frontcontroller.v4.ControllerV4;
import java.io.IOException;
import java.util.Map;
import javax.servlet.ServletException;

public class MemberListControllerV4 implements ControllerV4 {

  private final MemberRepository memberRepository = MemberRepository.getInstance();

  @Override
  public String process(Map<String, String> params, Map<String, Object> models) throws ServletException, IOException {
    models.put("members", memberRepository.findAll());
    return "members";
  }
}
