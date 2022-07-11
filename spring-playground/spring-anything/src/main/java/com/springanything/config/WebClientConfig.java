package com.springanything.config;

import java.time.Duration;
import java.util.Collections;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.http.codec.LoggingCodecSupport;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import io.netty.channel.ChannelOption;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(WebClientProperties.class)
public class WebClientConfig {

	private static final String CONNECTION_PROVIDER_NAME = "rview-provider";
	private static final Duration MAX_IDLE_AND_LIFE_TIME = Duration.ofSeconds(18);

	@Getter(value = AccessLevel.MODULE)
	private final WebClientProperties webClientProperties;

	@Bean
	public WebClient webClient() {
		return WebClient.builder()
			.clientConnector(
				new ReactorClientHttpConnector(
					HttpClient
						.create(getConnectionProvider())
						.secure(
							LambdaExceptionConsumer.toUnchecked(
								sslContextSpec -> sslContextSpec.sslContext(
									SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE).build()
								)
							)
						)
						.responseTimeout(Duration.ofSeconds(webClientProperties.responseTimeoutSeconds()))
						.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, webClientProperties.connectTimeoutMillis())
						.doOnConnected(connection -> connection
							.addHandlerLast(new ReadTimeoutHandler(webClientProperties.readWriteTimeoutSeconds()))
							.addHandlerLast(new WriteTimeoutHandler(webClientProperties.readWriteTimeoutSeconds()))
						)
				)
			)
			.exchangeStrategies(getExchangeStrategies())
			.filter(ExchangeFilterFunction.ofRequestProcessor(
				clientRequest -> {
					log.debug("Request: {} {}", clientRequest.method(), clientRequest.url());
					log.debug("Request Headers: {}", clientRequest.headers());
					return Mono.just(clientRequest);
				}
			))
			.filter(ExchangeFilterFunction.ofResponseProcessor(
				clientResponse -> {
					log.debug("Response : {}", clientResponse);
					log.debug("Response Headers : {}", clientResponse.headers().asHttpHeaders());
					return Mono.just(clientResponse);
				}
			))
			.defaultHeaders(this::getDefaultHeaders)
			.build();
	}

	private ConnectionProvider getConnectionProvider() {
		return ConnectionProvider.builder(CONNECTION_PROVIDER_NAME)
			.maxIdleTime(MAX_IDLE_AND_LIFE_TIME)
			.maxLifeTime(MAX_IDLE_AND_LIFE_TIME)
			.pendingAcquireMaxCount(-1)
			.pendingAcquireTimeout(Duration.ofSeconds(webClientProperties.responseTimeoutSeconds()))
			.lifo()
			.metrics(true)
			.build();
	}

	private ExchangeStrategies getExchangeStrategies() {
		ExchangeStrategies exchangeStrategies = ExchangeStrategies.builder()
			.codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(webClientProperties.maxByteCount()))
			.build();

		exchangeStrategies
			.messageWriters().stream()
			.filter(LoggingCodecSupport.class::isInstance)
			.forEach(writer -> ((LoggingCodecSupport)writer).setEnableLoggingRequestDetails(true));

		return exchangeStrategies;
	}

	private void getDefaultHeaders(HttpHeaders httpHeaders) {
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
	}
}
