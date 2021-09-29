package com.demo.coroutines.examples.background

import com.demo.coroutines.repository.GitHubRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import kotlin.concurrent.thread
import kotlin.system.measureTimeMillis

@Service
class RequestBackground(
    private val repository: GitHubRepository
) {
    private val log = LoggerFactory.getLogger(javaClass)

    fun loadContributorsBackground() {
        thread {
            log.info("======================== Start call =======================")
            val time = measureTimeMillis {
                repository.loadContributors()
            }
            log.info("======================== Finish duration: $time ms =======================")
        }
    }
}