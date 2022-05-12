package com.springservlet.web.frontcontroller.v5;

import com.springservlet.web.frontcontroller.ModelView;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ServletHandlerAdapter {

  boolean supports(Object handler);

  ModelView handle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws ServletException, IOException;
}
