package com.demo.coroutines.examples.blocking

import com.demo.coroutines.repository.GitHubRepository
import com.demo.coroutines.repository.entities.User
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import kotlin.system.measureTimeMillis

@Service
class RequestBlocking(
    private val repository: GitHubRepository
) {
    private val log = LoggerFactory.getLogger(javaClass)

    fun loadContributorsBlocking(): List<User> {
        var users: List<User>
        log.info("======================== Start call =======================")
        val time = measureTimeMillis {
            users = repository.loadContributors()
        }

        log.info("======================== Finish duration: $time ms =======================")
        return users
    }
}