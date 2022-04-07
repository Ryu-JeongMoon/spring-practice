wpackage com.example.ioc.event;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class OldEventHandler implements ApplicationListener<OldEvent> {
  @Override
  public void onApplicationEvent(OldEvent event) {
    System.out.println("Event Received, Data => " + event.getData());
  }
}
