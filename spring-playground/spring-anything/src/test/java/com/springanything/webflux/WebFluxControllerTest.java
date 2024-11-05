package com.springanything.webflux;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.springanything.AbstractIntegrationTest;
import com.springanything.jpa.relationship.Agent;
import com.springanything.jpa.relationship.AgentOption;
import com.springanything.jpa.relationship.AgentRepository;

import reactor.core.publisher.Flux;

@Disabled
class WebFluxControllerTest extends AbstractIntegrationTest {

  @Autowired
  AgentRepository agentRepository;

  @Test
  @DisplayName("Flux 안에서 blocking 코드도 문제 없다")
  void flux() throws InterruptedException {
    // given
    AgentOption option = AgentOption.builder()
      .name("panda")
      .age(155)
      .build();

    Agent agent1 = Agent.from(option);
    Agent agent2 = Agent.from(option);
    Agent agent3 = Agent.from(option);

    // when
    Flux.just(agent1, agent2, agent3)
      .log()
      .doOnNext(agentRepository::save)
      .subscribe();

    // then
    Thread.sleep(1000);
  }
}
