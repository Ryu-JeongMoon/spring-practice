package com.springservlet.web.frontcontroller.v2;

import com.springservlet.web.frontcontroller.MyView;
import com.springservlet.web.frontcontroller.v2.controller.MemberFormControllerV2;
import com.springservlet.web.frontcontroller.v2.controller.MemberListControllerV2;
import com.springservlet.web.frontcontroller.v2.controller.MemberSaveControllerV2;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/front-controller/v2/*")
public class FrontControllerV2 extends HttpServlet {

  private static final Map<String, ControllerV2> REQUEST_MAPPINGS = new ConcurrentHashMap<>(Map.of(
    "/front-controller/v2/members/new-form", new MemberFormControllerV2(),
    "/front-controller/v2/members/save", new MemberSaveControllerV2(),
    "/front-controller/v2/members", new MemberListControllerV2()
  ));

  @Override
  protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String requestURI = req.getRequestURI();
    ControllerV2 controller = REQUEST_MAPPINGS.get(requestURI);

    if (controller == null) {
      resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
      return;
    }

    MyView view = controller.process(req, resp);
    view.render(req, resp);
  }
}
