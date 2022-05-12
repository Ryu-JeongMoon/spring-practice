package com.springservlet.web.frontcontroller.v5;

import com.springservlet.web.frontcontroller.ModelView;
import com.springservlet.web.frontcontroller.MyView;
import com.springservlet.web.frontcontroller.v3.controller.MemberFormControllerV3;
import com.springservlet.web.frontcontroller.v3.controller.MemberListControllerV3;
import com.springservlet.web.frontcontroller.v3.controller.MemberSaveControllerV3;
import com.springservlet.web.frontcontroller.v4.controller.MemberFormControllerV4;
import com.springservlet.web.frontcontroller.v4.controller.MemberListControllerV4;
import com.springservlet.web.frontcontroller.v4.controller.MemberSaveControllerV4;
import com.springservlet.web.frontcontroller.v5.adapter.ControllerV3HandlerAdapter;
import com.springservlet.web.frontcontroller.v5.adapter.ControllerV4HandlerAdapter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/front-controller/v5/*")
public class FrontControllerV5 extends HttpServlet {

  private final Map<String, Object> REQUEST_MAPPING_CONTROLLER_MAPPINGS = new ConcurrentHashMap<>();
  private final List<ServletHandlerAdapter> HANDLER_ADAPTERS = new ArrayList<>();

  public FrontControllerV5() {
    initializeHandlerMappingControllerMappings();
    initializeHandlerAdapters();
  }

  private void initializeHandlerMappingControllerMappings() {
    REQUEST_MAPPING_CONTROLLER_MAPPINGS.putAll(Map.of(
      "/front-controller/v5/v3/members/new-form", new MemberFormControllerV3(),
      "/front-controller/v5/v3/members/save", new MemberSaveControllerV3(),
      "/front-controller/v5/v3/members", new MemberListControllerV3(),

      "/front-controller/v5/v4/members/new-form", new MemberFormControllerV4(),
      "/front-controller/v5/v4/members/save", new MemberSaveControllerV4(),
      "/front-controller/v5/v4/members", new MemberListControllerV4()
    ));
  }

  private void initializeHandlerAdapters() {
    HANDLER_ADAPTERS.addAll(List.of(new ControllerV3HandlerAdapter(), new ControllerV4HandlerAdapter()));
  }

  @Override
  protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    Object handler = getAdapter(req);
    if (handler == null) {
      resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
      return;
    }

    ServletHandlerAdapter handlerAdapter = getHandlerAdapter(handler);
    ModelView modelView = handlerAdapter.handle(req, resp, handler);

    MyView view = resolveView(modelView);
    view.render(modelView.getModel(), req, resp);
  }

  private Object getAdapter(HttpServletRequest req) {
    String requestURI = req.getRequestURI();
    return REQUEST_MAPPING_CONTROLLER_MAPPINGS.get(requestURI);
  }

  private ServletHandlerAdapter getHandlerAdapter(Object handler) {
    return HANDLER_ADAPTERS.stream()
      .filter(adapter -> adapter.supports(handler))
      .findAny()
      .orElseThrow(() -> new IllegalArgumentException("Handler Adapter Not Found {%s}".formatted(handler)));
  }

  private MyView resolveView(ModelView modelView) {
    return MyView.of("/WEB-INF/views/" + modelView.getViewName() + ".jsp");
  }

}
