package com.demo.coroutines.examples.suspends

import com.demo.coroutines.repository.GitHubRepository
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import kotlin.system.measureTimeMillis

@DelicateCoroutinesApi
@Service
class RequestSuspend(
    private val repository: GitHubRepository
) {
    private val log = LoggerFactory.getLogger(javaClass)

    suspend fun loadContributorsSuspend() {
        GlobalScope.launch {
            log.info("======================== Start call =======================")
            val time = measureTimeMillis {
                repository.loadContributorsSuspend()
            }
            log.info("======================== Finish duration: $time ms =======================")
        }
    }
}