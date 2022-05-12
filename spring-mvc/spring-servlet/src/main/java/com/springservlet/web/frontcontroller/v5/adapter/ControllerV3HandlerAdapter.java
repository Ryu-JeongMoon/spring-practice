package com.springservlet.web.frontcontroller.v5.adapter;

import com.springservlet.web.frontcontroller.ModelView;
import com.springservlet.web.frontcontroller.v3.ControllerV3;
import com.springservlet.web.frontcontroller.v5.ServletHandlerAdapter;
import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ControllerV3HandlerAdapter implements ServletHandlerAdapter {

  @Override
  public boolean supports(Object handler) {
    return handler instanceof ControllerV3;
  }

  @Override
  public ModelView handle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws ServletException, IOException {
    ControllerV3 controller = (ControllerV3) handler;
    Map<String, String> params = getParams(req);

    return controller.process(params);
  }

  private Map<String, String> getParams(HttpServletRequest req) {
    Map<String, String[]> parameterMap = req.getParameterMap();
    return parameterMap.keySet()
      .stream()
      .collect(Collectors.toMap(k -> k, k -> parameterMap.get(k)[0]));
  }
}
