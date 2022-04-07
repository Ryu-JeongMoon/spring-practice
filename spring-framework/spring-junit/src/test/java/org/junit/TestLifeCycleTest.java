package org.junit;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.domain.Study;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TestLifeCycleTest {

  @BeforeAll
  static void beforeAll() {
    System.out.println("StudyTest.beforeAll");
  }

  @AfterAll
  static void afterAll() {
    System.out.println("StudyTest.afterAll");
  }

  @Test
  @DisplayName("YAHOO 👍")
  void create_new_study() {
    Study study = new Study();
    assertNotNull(study);
  }

  @Test
  @DisplayName("우헤헤 😀")
  void create_new_study_again() {
    System.out.println("StudyTest.create2");
  }

  @BeforeEach
  void beforeEach() {
    System.out.println("StudyTest.beforeEach");
  }

  @AfterEach
  void afterEach() {
    System.out.println("StudyTest.afterEach");
  }
}

/*
이모티콘도 되네잉
 */