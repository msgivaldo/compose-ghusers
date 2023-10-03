package dev.givaldo.ghusers.data.network.ktor

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.cache.HttpCache
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import java.util.concurrent.TimeUnit

interface HttpClientProvider {
    val client: HttpClient
}

internal class GHUsersHttpClientProvider : HttpClientProvider {
    override val client = httpClient {
        install(ContentNegotiation) {
            json(
                Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                },
            )
        }
        install(HttpCache)
        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    println("CarSpecs.HttpClient :$message")
                }
            }
            level = LogLevel.BODY
        }
        defaultRequest {
            url("https://api.github.com")
        }
    }
}
fun httpClient(config: HttpClientConfig<*>.() -> Unit) = HttpClient(OkHttp) {
    config(this)

    engine {
        config {
            retryOnConnectionFailure(false)
            connectTimeout(30, TimeUnit.SECONDS)
        }
    }
}
