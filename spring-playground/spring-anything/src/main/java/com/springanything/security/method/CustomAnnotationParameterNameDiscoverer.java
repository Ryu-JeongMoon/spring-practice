package com.springanything.security.method;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;

import org.springframework.security.core.parameters.AnnotationParameterNameDiscoverer;

public class CustomAnnotationParameterNameDiscoverer extends AnnotationParameterNameDiscoverer {

  public static final String USER_ID_OBJECT = "userIdObject";

  public CustomAnnotationParameterNameDiscoverer() {
    super(UserIdParameter.class.getName());
  }

  @Override
  public String[] getParameterNames(Method method) {
    Parameter[] parameters = method.getParameters();
    String[] parameterNames = new String[parameters.length];
    for (int i = 0; i < parameters.length; i++) {
      if (existsIdInParameter(parameters[i]) || existsIdInObjectParameter(parameters[i].getType())) {
        parameterNames[i] = USER_ID_OBJECT;
        break;
      }
    }
    return parameterNames;
  }

  private boolean existsIdInParameter(AnnotatedElement parameter) {
    return parameter.isAnnotationPresent(UserIdParameter.class);
  }

  private boolean existsIdInObjectParameter(Class<?> clazz) {
    return ApiRequest.class.isAssignableFrom(clazz)
      && Arrays.stream(clazz.getDeclaredFields())
      .anyMatch(this::existsIdInParameter);
  }
}
