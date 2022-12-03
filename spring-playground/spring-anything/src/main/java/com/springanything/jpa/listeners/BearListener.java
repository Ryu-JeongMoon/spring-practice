package com.springanything.jpa.listeners;

import org.hibernate.event.spi.PostDeleteEvent;
import org.hibernate.event.spi.PostDeleteEventListener;
import org.hibernate.persister.entity.EntityPersister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.springanything.jpa.Bear;
import com.springanything.jpa.PandaService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class BearListener implements JpaEventListener, PostDeleteEventListener {

	private static PandaService pandaService;

/*
	todo,
	 이 위치에서 ApplicationContext에서 빈을 뽑아서 주입하려 하면 예외 발생함
	 왜인고 허니 EventListenerIntegrator 등록할 때 META-INF/services/org.hibernate.integrator.spi.Integrator로 등록해버리는데
	 Context 생성 전에 등록되는 것이므로 빈을 읽어올 수가 없둥
	public BambooListener(ApplicationContext ctx) {
		pandaRepository = ctx.getBean(PandaRepository.class);
	}
*/

	// static 으로 주입해주는 방법도 있둥,
	// 보기엔 이상하더라도 지연 주입을 해야하기 땜시 setter, 혹은 이름 제멋대로 지으면 됨
	@Autowired
	public void setPandaService(PandaService pandaService) {
		BearListener.pandaService = pandaService;
	}

	@Override
	public void onPostDelete(PostDeleteEvent event) {
		log.info("[START] BearListener.onPostDelete");
		log.info("onPostDelete.thread : {}", Thread.currentThread().getId());

		final Object entity = event.getEntity();
		if (!(entity instanceof Bear bear)) {
			return;
		}

		event.getSession().getActionQueue().registerProcess((success, sessionImplementor) -> {
			if (success) {
				doProcess(sessionImplementor, session -> pandaService.deleteByBearId(bear.getId(), session));
			}
		});
	}

	// 요놈에다가 true 줘야 하이버네이트 내부에서 호출할 때 처리해야 할 이벤트가 있다고 인식함둥
	@Override
	public boolean requiresPostCommitHanding(EntityPersister persister) {
		return true;
	}
}

/*
session.createSQLQuery("delete from panda where bear_id = :bearId")
		.setParameter("bearId", bear.getId())
		.executeUpdate();
 */