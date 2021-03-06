package org.junit.domain;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Study {

  @Id
  @GeneratedValue
  private Long id;
  private StudyStatus status;
  private int limitCount;
  private String name;
  private LocalDateTime openedDateTime;
  private Long ownerId;

  public Study(int limit, String name) {
    this.limitCount = limit;
    this.name = name;
  }

  public Study(int limit) {
    if (limit < 0) {
      throw new IllegalArgumentException("limit은 0보다 커야 한다.");
    }
    this.limitCount = limit;
  }

  public void open() {
    this.openedDateTime = LocalDateTime.now();
    this.status = StudyStatus.OPENED;

  }

}
