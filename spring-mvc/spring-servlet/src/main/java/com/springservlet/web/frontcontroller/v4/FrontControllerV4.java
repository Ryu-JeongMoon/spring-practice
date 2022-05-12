package com.springservlet.web.frontcontroller.v4;

import com.springservlet.web.frontcontroller.ModelView;
import com.springservlet.web.frontcontroller.MyView;
import com.springservlet.web.frontcontroller.v4.controller.MemberFormControllerV4;
import com.springservlet.web.frontcontroller.v4.controller.MemberListControllerV4;
import com.springservlet.web.frontcontroller.v4.controller.MemberSaveControllerV4;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/front-controller/v4/*")
public class FrontControllerV4 extends HttpServlet {

  private static final Map<String, ControllerV4> REQUEST_MAPPINGS = new ConcurrentHashMap<>(Map.of(
    "/front-controller/v4/members/new-form", new MemberFormControllerV4(),
    "/front-controller/v4/members/save", new MemberSaveControllerV4(),
    "/front-controller/v4/members", new MemberListControllerV4()
  ));

  @Override
  protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    String requestURI = req.getRequestURI();
    ControllerV4 controller = REQUEST_MAPPINGS.get(requestURI);

    if (controller == null) {
      resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
      return;
    }

    Map<String, Object> models = new ConcurrentHashMap<>();
    String viewName = controller.process(getParams(req), models);

    ModelView modelView = ModelView.of(viewName);
    MyView view = resolveView(modelView);
    view.render(models, req, resp);
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

/*
FrontController 자체에서 Spring의 Model과 같은 형태로 Map<String, Object>를 생성해서 넘겨버린다
ControllerV4를 구현하는 개발자 입장에서는 전달해야 할 데이터는 인수로 넘어오는 Map에 저장하고
뷰의 논리명만 넘겨주면 FrontController에서 뷰 이름도 알아서 완성시키고, ModelView 생성도 알아서 해준다?!
개발자 입장에서 ModelView 생성할 필요 없이 데이터 넣고, 뷰 이름만 반환해주면 되서 간단!
 */