package com.demo.coroutines.repository

import com.demo.coroutines.repository.entities.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux

@Component
class GitHubRepository(
    private val service: GitHubServices
) {
    fun loadContributors(): List<User> {
        val repos = service.repos()
        return repos.flatMap { repo ->
            service.repoContributors(repo = repo.name)
        }
    }

    suspend fun loadContributorsSuspend(): List<User> {
        val repos = service.repos()
        return repos.flatMap { repo ->
            service.repoContributors(repo = repo.name)
        }
    }

    suspend fun loadContributorsConcurrency(): List<User> = coroutineScope {
        val repos = service.repos()
        repos.map { repo ->
            async(Dispatchers.Default) {
                service.repoContributors(repo = repo.name)
            }
        }.awaitAll().flatten()
    }

    suspend fun loadContributorsChannels(): List<User> = coroutineScope {
        val repos = service.repos()
        val channel = Channel<List<User>>()

        for (repo in repos) {
            launch {
                val users = service.repoContributors(repo = repo.name)
                channel.send(users)
            }
        }

        var allUsers = emptyList<User>()

        repeat(repos.size) {
            val users = channel.receive()
            allUsers = (allUsers + users)
        }

        allUsers
    }

    fun loadContributorsUsingWebFlux(): Flux<User> {
        return service.reposWithWebClient()
    }
}