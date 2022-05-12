package com.springservlet.web.frontcontroller.v2;

import com.springservlet.web.frontcontroller.MyView;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ControllerV2 {

  MyView process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;
}
