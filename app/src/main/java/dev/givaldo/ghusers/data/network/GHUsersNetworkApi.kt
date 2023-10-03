package dev.givaldo.ghusers.data.network

import dev.givaldo.ghusers.data.network.ktor.GHUsersHttpClientProvider
import dev.givaldo.ghusers.data.network.ktor.HttpClientProvider
import dev.givaldo.ghusers.data.network.ktor.response.GitHubUserDetailResponse
import dev.givaldo.ghusers.data.network.ktor.response.GitHubUserResponse
import dev.givaldo.ghusers.data.network.ktor.response.GithubReposResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import java.io.IOException

interface GHUsersNetworkApi {
    suspend fun getUsers(): List<GitHubUserResponse>
    suspend fun getUserDetail(username: String): GitHubUserDetailResponse
    suspend fun getUserRepos(username: String): List<GithubReposResponse>
}

class KtorGHUsersNetworkApi(
    private val clientProvider: HttpClientProvider = GHUsersHttpClientProvider()
) : GHUsersNetworkApi {
    override suspend fun getUsers(): List<GitHubUserResponse> {
        val response = clientProvider.client.getOrException("users")
        return response.body()
    }

    override suspend fun getUserDetail(username: String): GitHubUserDetailResponse {
        val response = clientProvider.client.getOrException("users/$username")
        return response.body()
    }

    override suspend fun getUserRepos(username: String): List<GithubReposResponse> {
        val response = clientProvider.client.getOrException("users/$username/repos")
        return response.body()
    }
}

suspend inline fun HttpClient.getOrException(
    urlString: String,
    block: HttpRequestBuilder.() -> Unit = {},
): HttpResponse = try {
    get(urlString) {
        contentType(ContentType.Application.Json)
        block()
    }
} catch (e: IOException) {
    throw NetworkError.NetworkFailure(e)
} catch (t: Throwable) {
    throw NetworkError.UnknownFailure(t)
}


sealed class NetworkError(
    override val message: String?,
    override val cause: Throwable,
) : Exception(cause) {

    class NetworkFailure(cause: Exception) : NetworkError(
        message = "No internet connection. Please try again later",
        cause = cause,
    )

    class UnknownFailure(throwable: Throwable) : NetworkError(
        message = "An unknown error happened. Please try again later",
        cause = throwable,
    )
}
