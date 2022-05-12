package com.springservlet.web.frontcontroller.v3;

import com.springservlet.web.frontcontroller.ModelView;
import java.io.IOException;
import java.util.Map;
import javax.servlet.ServletException;

public interface ControllerV3 {

  ModelView process(Map<String, String> params) throws ServletException, IOException;
}
