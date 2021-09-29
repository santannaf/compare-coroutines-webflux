package com.demo.coroutines.examples.webflux

import com.demo.coroutines.repository.GitHubRepository
import com.demo.coroutines.repository.entities.User
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
class RequestWebFlux(
    private val repository: GitHubRepository
) {
    private val log = LoggerFactory.getLogger(javaClass)

    fun loadContributorsWebFlux(): Flux<User> {
        log.info("======================== Start call =======================")
        return repository.loadContributorsUsingWebFlux()
    }
}