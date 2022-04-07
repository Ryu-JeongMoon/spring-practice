package com.example.ioc.runner;

import com.example.ioc.validator.ValidObject;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Arrays;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class ValidatorRunner implements ApplicationRunner {

  private final Validator validator;

  @Override
  public void run(ApplicationArguments args) throws Exception {
    System.out.println("=================================================================");
    System.out.println("ValidatorRunner.run");

    ValidObject validObject = new ValidObject();
    validObject.setAge(-1);

//    ValidObjectValidator validator = new ValidObjectValidator();
    Errors errors = new BeanPropertyBindingResult(validObject, "validObject");

    validator.validate(validObject, errors);

    System.out.println("validator.getClass() = " + validator.getClass());
    System.out.println("errors.hasErrors() = " + errors.hasErrors());

    errors.getAllErrors().forEach(
        e -> Arrays.stream(Objects.requireNonNull(e.getCodes())).forEach(System.out::println)
    );

    System.out.println("=================================================================\n");
  }
}
