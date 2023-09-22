package com.springanything.mutlithread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StartingWithDigit {

  public static void main(String[] args) throws IOException {
    String regex = new BufferedReader(new InputStreamReader(System.in)).readLine();

    //Creating a Pattern object
    Pattern p = Pattern.compile(regex);
    log.info("Pattern was made!");

    final String input = "Linux Hoy!";

    for (int i = 0; i < 10; i++) {
      //Creating a Matcher object
      Matcher m = p.matcher(input);
      boolean isFound = m.find();
      if (isFound) {
        log.info("[Main-Thread] {} : {}", i, true);
      }
    }

    new Thread(() -> {
      for (int i = 0; i < 10; i++) {
        Matcher m = p.matcher(input);
        boolean isFound = m.find();
        if (isFound) {
          log.info("[Thread-1] {} : {}", i, isFound);
        }
      }
    }).start();

    new Thread(() -> {
      for (int i = 0; i < 10; i++) {
        Matcher m = p.matcher(input);
        boolean isFound = m.find();
        if (isFound) {
          log.info("[Thread-2] {} : {}", i, isFound);
        }
      }
    }).start();
  }
}
