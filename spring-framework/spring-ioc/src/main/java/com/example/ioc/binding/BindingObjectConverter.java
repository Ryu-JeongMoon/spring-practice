package com.example.ioc.binding;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

public class BindingObjectConverter {

  @Component
  public static class StringToBindingObjectConverter implements Converter<String, BindingObject> {

    @Override
    public BindingObject convert(String source) {
      return new BindingObject(Long.parseLong(source));
    }
  }

  @Component
  public static class BindingObjectToStringConverter implements Converter<BindingObject, String> {

    @Override
    public String convert(BindingObject source) {
      return source.getId().toString();
    }
  }
}

/*
converter 자체를 bean 으로 등록해야 함
 */