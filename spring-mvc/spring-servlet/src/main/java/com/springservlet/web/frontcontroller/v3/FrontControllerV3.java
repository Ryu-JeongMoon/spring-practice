package com.springservlet.web.frontcontroller.v3;

import com.springservlet.web.frontcontroller.ModelView;
import com.springservlet.web.frontcontroller.MyView;
import com.springservlet.web.frontcontroller.v3.controller.MemberFormControllerV3;
import com.springservlet.web.frontcontroller.v3.controller.MemberListControllerV3;
import com.springservlet.web.frontcontroller.v3.controller.MemberSaveControllerV3;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/front-controller/v3/*")
public class FrontControllerV3 extends HttpServlet {

  private static final Map<String, ControllerV3> REQUEST_MAPPINGS = new ConcurrentHashMap<>(Map.of(
    "/front-controller/v3/members/new-form", new MemberFormControllerV3(),
    "/front-controller/v3/members/save", new MemberSaveControllerV3(),
    "/front-controller/v3/members", new MemberListControllerV3()
  ));

  @Override
  protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String requestURI = req.getRequestURI();
    ControllerV3 controller = REQUEST_MAPPINGS.get(requestURI);

    if (controller == null) {
      resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
      return;
    }

    Map<String, String> params = getParams(req);
    ModelView modelView = controller.process(params);

    MyView view = resolveView(modelView);
    view.render(modelView.getModel(), req, resp);
  }

  private MyView resolveView(ModelView modelView) {
    return MyView.of("/WEB-INF/views/" + modelView.getViewName() + ".jsp");
  }

  private Map<String, String> getParams(HttpServletRequest req) {
    Map<String, String[]> params = req.getParameterMap();
    return params.keySet()
      .stream()
      .collect(Collectors.toMap(key -> key, key -> req.getParameterMap().get(key)[0], (key, duplicatedKey) -> key));
  }
}
