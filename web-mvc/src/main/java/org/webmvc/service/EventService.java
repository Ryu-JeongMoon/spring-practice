package org.webmvc.service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import org.webmvc.domain.Event;

@Service
public class EventService {

  public List<Event> getEvents() {
    Event event1 = Event.builder()
      .name("spring web-mvc 1")
      .limitOfEnrollment(5)
      .startDateTime(LocalDateTime.of(2019, 1, 1, 10, 0, 0))
      .endDateTime(LocalDateTime.of(2019, 1, 1, 12, 0, 0))
      .build();

    Event event2 = Event.builder()
      .name("spring web-mvc 1")
      .limitOfEnrollment(5)
      .startDateTime(LocalDateTime.of(2019, 1, 2, 10, 0, 0))
      .endDateTime(LocalDateTime.of(2019, 1, 2, 12, 0, 0))
      .build();

    return List.of(event1, event2);
  }
}
