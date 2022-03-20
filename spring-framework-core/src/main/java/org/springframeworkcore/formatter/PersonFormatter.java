package org.springframeworkcore.formatter;

import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class PersonFormatter implements Formatter<Person> {

  @Override
  public Person parse(String text, Locale locale) {
    return Person.builder()
        .name(text)
        .build();
  }

  @Override
  public String print(Person object, Locale locale) {
    return object.toString();
  }
}
