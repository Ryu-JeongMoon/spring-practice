package com.springservlet.web.frontcontroller.v4;

import java.io.IOException;
import java.util.Map;
import javax.servlet.ServletException;

public interface ControllerV4 {

  String process(Map<String, String> params, Map<String, Object> models) throws ServletException, IOException;
}
