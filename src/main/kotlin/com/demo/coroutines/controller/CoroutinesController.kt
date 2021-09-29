package com.demo.coroutines.controller

import com.demo.coroutines.examples.background.RequestBackground
import com.demo.coroutines.examples.blocking.RequestBlocking
import com.demo.coroutines.examples.channels.RequestChannels
import com.demo.coroutines.examples.concurrency.RequestConcurrent
import com.demo.coroutines.examples.suspends.RequestSuspend
import com.demo.coroutines.examples.webflux.RequestWebFlux
import com.demo.coroutines.repository.entities.User
import kotlinx.coroutines.DelicateCoroutinesApi
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux

@DelicateCoroutinesApi
@RestController
@RequestMapping(path = ["/coroutines"])
class CoroutinesController(
    private val blocking: RequestBlocking,
    private val background: RequestBackground,
    private val suspend: RequestSuspend,
    private val concurrent: RequestConcurrent,
    private val channels: RequestChannels,
    private val webflux: RequestWebFlux,
) {
    @GetMapping(path = ["/blocking"])
    fun contributorsBlocking(): List<User> {
        return blocking.loadContributorsBlocking()
    }

    @GetMapping(path = ["/background"])
    fun contributorsBackground() {
        background.loadContributorsBackground()
    }

    @GetMapping(path = ["/suspend"])
    suspend fun contributorsSuspend() {
        suspend.loadContributorsSuspend()
    }

    @GetMapping(path = ["/concurrent"])
    suspend fun contributorsConcurrent() {
        concurrent.loadContributorsConcurrent()
    }

    @GetMapping(path = ["/channels"])
    suspend fun contributorsChannels(): List<User> {
        return channels.loadContributorsChannels()
    }

    @GetMapping(path = ["/webflux"])
    fun contributorsUsingWebFlux(): Flux<User> {
        return webflux.loadContributorsWebFlux()
    }
}