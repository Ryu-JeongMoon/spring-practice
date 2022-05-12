package com.springservlet.web.frontcontroller.v1;

import com.springservlet.web.frontcontroller.v1.controller.MemberFormControllerV1;
import com.springservlet.web.frontcontroller.v1.controller.MemberListControllerV1;
import com.springservlet.web.frontcontroller.v1.controller.MemberSaveControllerV1;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/front-controller/v1/*")
public class FrontControllerV1 extends HttpServlet {

  private static final Map<String, ControllerV1> REQUEST_MAPPINGS = new ConcurrentHashMap<>(Map.of(
    "/front-controller/v1/members/new-form", new MemberFormControllerV1(),
    "/front-controller/v1/members/save", new MemberSaveControllerV1(),
    "/front-controller/v1/members", new MemberListControllerV1()
  ));

  @Override
  protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String requestURI = req.getRequestURI();
    ControllerV1 controller = REQUEST_MAPPINGS.get(requestURI);

    if (controller == null) {
      resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
      return;
    }

    controller.process(req, resp);
  }
}
