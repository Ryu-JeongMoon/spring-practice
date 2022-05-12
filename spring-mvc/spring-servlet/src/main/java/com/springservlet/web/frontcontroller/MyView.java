package com.springservlet.web.frontcontroller;

import java.io.IOException;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public record MyView(String viewPath) {

  public static MyView of(String viewPath) {
    return new MyView(viewPath);
  }

  public void render(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    RequestDispatcher dispatcher = req.getRequestDispatcher(viewPath);
    dispatcher.forward(req, resp);
  }

  public void render(Map<String, Object> model, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    model.forEach(req::setAttribute);
    render(req, resp);
  }
}
