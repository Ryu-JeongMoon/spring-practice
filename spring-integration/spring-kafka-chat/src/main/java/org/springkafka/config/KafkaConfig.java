package org.springkafka.config;

import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springkafka.SpringKafkaApplication;

@EnableKafka
@EnableIntegration
@IntegrationComponentScan(basePackageClasses = SpringKafkaApplication.class)
public class KafkaConfig {
}
