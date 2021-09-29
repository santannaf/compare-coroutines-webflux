package com.demo.coroutines.config

import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.web.reactive.function.client.ExchangeFilterFunction
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Configuration
class WebClientConfig {
    private val log = LoggerFactory.getLogger(javaClass)

    fun logRequest(): ExchangeFilterFunction {
        return ExchangeFilterFunction.ofRequestProcessor { request ->
            log.info("m=logRequest; url=${request.url()};")
            Mono.just(request)
        }
    }

    @Bean
    fun webClient(): WebClient {
        return WebClient.builder().baseUrl(CommonsUtils.URI_BASE)
            .defaultHeader(HttpHeaders.CONTENT_TYPE, "application/vnd.github.v3+json")
            .filter(logRequest()).build()
    }
}