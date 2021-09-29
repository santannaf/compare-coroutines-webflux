package com.demo.coroutines.repository

import com.demo.coroutines.config.CommonsUtils.Companion.generateBasicAuth
import com.demo.coroutines.config.CommonsUtils.Companion.generateGetHttpRequest
import com.demo.coroutines.repository.entities.Repo
import com.demo.coroutines.repository.entities.User
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux
import java.net.http.HttpClient
import java.net.http.HttpResponse

@Component
class GitHubServices(
    @Value("\${coroutines.username}")
    private val username: String,
    @Value("\${coroutines.password}")
    private val password: String,
    private val webClient: WebClient
) {
    private val mapper = jacksonObjectMapper()
    private val log = LoggerFactory.getLogger(javaClass)

    @Cacheable(value = ["repos"])
    fun repos(org: String = "kotlin"): List<Repo> {
        val request = generateGetHttpRequest("orgs/$org/repos?per_page=100", generateBasicAuth(username, password))
        val response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString())
        return mapper.readValue(response.body(), object : TypeReference<List<Repo>>() {}).also {
            log.info("kotlin: loaded ${it.size} repos")
        }
    }

    @Cacheable(value = ["repoContributors"], key = "#repo")
    fun repoContributors(owner: String = "kotlin", repo: String): List<User> {
        val request = generateGetHttpRequest(
            "repos/$owner/$repo/contributors?per_page=100", generateBasicAuth(username, password)
        )
        val response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString())
        return mapper.readValue(response.body(), object : TypeReference<List<User>>() {})
            .also { users -> log.info("${repo}: loaded ${users.size} contributors") }
    }

    fun reposWithWebClient(org: String = "kotlin"): Flux<User> {
        return webClient.get()
            .uri("/orgs/$org/repos?per_page=100")
            .header("Authorization", generateBasicAuth(username, password))
            .exchangeToFlux { clientResponse -> clientResponse.bodyToFlux(Repo::class.java) }
            .flatMap {
                webClient.get()
                    .uri("/repos/$org/${it.name}/contributors?per_page=100")
                    .header("Authorization", generateBasicAuth(username, password))
                    .retrieve()
                    .bodyToFlux(User::class.java)
            }
    }
}