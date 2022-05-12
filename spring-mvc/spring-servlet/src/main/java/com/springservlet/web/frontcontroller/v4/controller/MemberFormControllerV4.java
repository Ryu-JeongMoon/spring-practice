package com.springservlet.web.frontcontroller.v4.controller;

import com.springservlet.web.frontcontroller.v4.ControllerV4;
import java.io.IOException;
import java.util.Map;
import javax.servlet.ServletException;

public class MemberFormControllerV4 implements ControllerV4 {

  @Override
  public String process(Map<String, String> params, Map<String, Object> models) throws ServletException, IOException {
    return "new-form";
  }
}
