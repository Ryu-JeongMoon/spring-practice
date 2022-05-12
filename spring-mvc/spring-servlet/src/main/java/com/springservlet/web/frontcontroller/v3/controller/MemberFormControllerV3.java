package com.springservlet.web.frontcontroller.v3.controller;

import com.springservlet.web.frontcontroller.ModelView;
import com.springservlet.web.frontcontroller.v3.ControllerV3;
import java.io.IOException;
import java.util.Map;
import javax.servlet.ServletException;

public class MemberFormControllerV3 implements ControllerV3 {

  @Override
  public ModelView process(Map<String, String> params) throws ServletException, IOException {
    return ModelView.of("new-form");
  }
}
