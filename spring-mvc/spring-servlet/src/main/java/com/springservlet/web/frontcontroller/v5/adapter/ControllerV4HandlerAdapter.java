package com.springservlet.web.frontcontroller.v5.adapter;

import com.springservlet.web.frontcontroller.ModelView;
import com.springservlet.web.frontcontroller.v4.ControllerV4;
import com.springservlet.web.frontcontroller.v5.ServletHandlerAdapter;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ControllerV4HandlerAdapter implements ServletHandlerAdapter {

  @Override
  public boolean supports(Object handler) {
    return handler instanceof ControllerV4;
  }

  @Override
  public ModelView handle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws ServletException, IOException {
    ControllerV4 controller = (ControllerV4) handler;

    Map<String, String> params = getParams(req);
    Map<String, Object> models = new ConcurrentHashMap<>();
    String viewName = controller.process(params, models);

    ModelView modelView = ModelView.of(viewName);
    modelView.addAttributes(models);
    return modelView;
  }

  private Map<String, String> getParams(HttpServletRequest req) {
    Map<String, String[]> parameterMap = req.getParameterMap();
    return parameterMap.keySet()
      .stream()
      .collect(Collectors.toMap(k -> k, k -> parameterMap.get(k)[0]));
  }
}
