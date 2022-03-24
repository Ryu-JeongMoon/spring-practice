package org.junit;

import lombok.Getter;

@Getter
public class Study {

  private final StudyStatus status = StudyStatus.DRAFT;
  private int limitOfStudents;
}
