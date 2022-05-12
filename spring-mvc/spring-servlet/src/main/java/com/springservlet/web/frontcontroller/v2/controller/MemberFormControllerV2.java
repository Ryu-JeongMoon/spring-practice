package com.springservlet.web.frontcontroller.v2.controller;

import com.springservlet.web.frontcontroller.MyView;
import com.springservlet.web.frontcontroller.v2.ControllerV2;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MemberFormControllerV2 implements ControllerV2 {

  @Override
  public MyView process(HttpServletRequest req, HttpServletResponse resp) {
    return MyView.of("/WEB-INF/views/new-form.jsp");
  }
}
