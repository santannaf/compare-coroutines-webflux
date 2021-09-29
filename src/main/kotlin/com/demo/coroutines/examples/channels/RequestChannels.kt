package com.demo.coroutines.examples.channels

import com.demo.coroutines.repository.GitHubRepository
import com.demo.coroutines.repository.entities.User
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import kotlin.system.measureTimeMillis
@DelicateCoroutinesApi
@Service
class RequestChannels(
    private val repository: GitHubRepository
) {
    private val log = LoggerFactory.getLogger(javaClass)

    suspend fun loadContributorsChannels(): List<User> {
        var users: List<User> = emptyList()
        GlobalScope.launch {
            log.info("======================== Start call =======================")
            val time = measureTimeMillis {
                users = repository.loadContributorsChannels()
            }
            log.info("======================== Finish duration: $time ms =======================")
        }.join()

        log.info("Size Users >>>> ${users.size}")
        return users
    }
}