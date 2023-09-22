package com.springanything.jpa.listeners;

import java.util.function.Consumer;

import org.hibernate.Session;
import org.hibernate.SharedSessionContract;
import org.hibernate.Transaction;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface JpaEventListener {

  Logger log = LoggerFactory.getLogger(JpaEventListener.class);

  default void doProcess(
    SharedSessionContractImplementor eventSession,
    Consumer<Session> task
  ) {
    log.info("eventSession : {}", eventSession);
    try (Session openedSession = eventSession.getFactory().openSession()) {
      log.info("openedSession : {}", openedSession);
      commitOrRollback(openedSession, task);
    } catch (Exception ex) {
      log.error("error:{}", ex.getMessage(), ex);
    }

    log.trace("end..");
  }

  default <T extends SharedSessionContract> void commitOrRollback(
    T openedSession,
    Consumer<T> task
  ) {
    Transaction tx = null;
    try {
      tx = openedSession.beginTransaction();
      task.accept(openedSession);
      tx.commit();
    } catch (Exception ex) {
      if (tx != null) {
        tx.rollback();
      }
      throw ex;
    }
  }
}
