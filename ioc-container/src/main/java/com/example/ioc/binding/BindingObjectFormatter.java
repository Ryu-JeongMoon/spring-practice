package com.example.ioc.binding;

import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Locale;

@Component
public class BindingObjectFormatter implements Formatter<BindingObject> {

  @Override
  public BindingObject parse(String text, Locale locale) throws ParseException {
    return new BindingObject(Long.parseLong(text));
  }

  @Override
  public String print(BindingObject object, Locale locale) {
    return object.getId().toString();
  }
}