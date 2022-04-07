package org.junit;

import org.junit.jupiter.api.DisplayName;

public class StudyWithJunit4Test {

  @Test
  @DisplayName("YAHOO")
  public void createWithJunit4() {
    System.out.println("StudyWithJunit4Test.createWithJunit4");
  }

  @Test
  public void createWithJunit4Second() {
    System.out.println("StudyWithJunit4Test.createWithJunit4Second");
  }
}
