package org.junit;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Study {

  private StudyStatus status = StudyStatus.DRAFT;
  private int limitOfStudents;
  private String name;
}
