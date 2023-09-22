package com.springanything.jpa.listeners;

import org.hibernate.boot.Metadata;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.integrator.spi.Integrator;
import org.hibernate.service.spi.SessionFactoryServiceRegistry;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.springanything.jpa.JpaEventListenerConfig;

public class EventListenerIntegrator implements Integrator {

  @Override
  @SuppressWarnings("deprecation")
  public void integrate(Metadata metadata, SessionFactoryImplementor sessionFactory, SessionFactoryServiceRegistry serviceRegistry) {
    EventListenerRegistry eventListenerRegistry =
      serviceRegistry.getService(EventListenerRegistry.class);

    // SpringAnythingApplication 기준으로 context 생성하니까 컨텍스트 재귀 생성됨.. 요기 주의
    AnnotationConfigApplicationContext ctx =
      new AnnotationConfigApplicationContext(JpaEventListenerConfig.class);

    // Integrator 인터페이스 구현한 클래스에서 커스텀 리스너를 등록해준다
    // POST_DELETE 외에도 다양한 타입 지정 가능
    eventListenerRegistry.getEventListenerGroup(EventType.POST_COMMIT_DELETE)
      .appendListener(new BearListener());
  }

  @Override
  public void disintegrate(SessionFactoryImplementor sessionFactory, SessionFactoryServiceRegistry serviceRegistry) {
  }
}
