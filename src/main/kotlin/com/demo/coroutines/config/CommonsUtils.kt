package com.demo.coroutines.config

import java.net.URI
import java.net.http.HttpRequest
import java.util.*

class CommonsUtils {
    companion object {
        const val URI_BASE: String = "https://api.github.com"

        fun generateBasicAuth(username: String, password: String): String =
            "Basic " + Base64.getEncoder().encode("$username:$password".toByteArray()).toString(Charsets.UTF_8)

        fun generateGetHttpRequest(uri: String, auth: String): HttpRequest =
            HttpRequest.newBuilder(URI("$URI_BASE/$uri"))
                .headers(
                    "Accept", "application/vnd.github.v3+json",
                    "Authorization", auth,
                    "Content-Type", "application/json"
                )
                .GET()
                .build()
    }
}