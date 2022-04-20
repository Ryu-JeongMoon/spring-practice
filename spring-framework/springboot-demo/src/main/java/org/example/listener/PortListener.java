package org.example.listener;

import org.springframework.boot.web.context.WebServerApplicationContext;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class PortListener implements ApplicationListener<WebServerInitializedEvent> {

  @Override
  public void onApplicationEvent(WebServerInitializedEvent event) {
    WebServerApplicationContext applicationContext = event.getApplicationContext();
    int port = applicationContext.getWebServer().getPort();
    System.out.println("port = " + port);
  }
}
