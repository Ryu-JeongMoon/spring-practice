package com.springservlet.web.frontcontroller;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ModelView {

  private final String viewName;
  private final Map<String, Object> model = new ConcurrentHashMap<>();

  public void addAttribute(String name, Object value) {
    model.put(name, value);
  }

  public static ModelView of(String viewName) {
    return new ModelView(viewName);
  }

  public void addAttributes(Map<String, Object> models) {
    model.putAll(models);
  }
}
