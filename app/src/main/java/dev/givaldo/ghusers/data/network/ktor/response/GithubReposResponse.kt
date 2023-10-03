package dev.givaldo.ghusers.data.network.ktor.response

import dev.givaldo.ghusers.domain.model.GithubRepo
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GithubReposResponse(
    val id: Int,
    val name: String,
    @SerialName("full_name") val fullName: String,
    val private: Boolean,
    val owner: OwnerResponse,
    @SerialName("html_url") val htmlUrl: String,
    val description: String?,
    val fork: Boolean,
    val url: String,
    val homepage: String?,
    val size: Int,
    @SerialName("watchers_count") val watchersCount: Int,
    val language: String?,
    @SerialName("has_issues") val hasIssues: Boolean,
    @SerialName("mirror_url") val mirrorUrl: String?,
    val archived: Boolean,
    val disabled: Boolean,
    @SerialName("open_issues_count") val openIssuesCount: Int,
    @SerialName("allow_forking") val allowForking: Boolean,
    @SerialName("open_issues") val openIssues: Int,
    val watchers: Int,
    @SerialName("default_branch") val defaultBranch: String
) {
    fun toDomain(): GithubRepo {
        return GithubRepo(
            id = id,
            login = owner.login,
            name = name,
            fullName = fullName,
            private = private,
            htmlUrl = htmlUrl,
            description = description,
            fork = fork,
            url = url,
            homepage = homepage,
            size = size,
            watchersCount = watchersCount,
            language = language,
            hasIssues = hasIssues,
            mirrorUrl = mirrorUrl,
            archived = archived,
            disabled = disabled,
            openIssuesCount = openIssuesCount,
            allowForking = allowForking,
            openIssues = openIssues,
            watchers = watchers,
            defaultBranch = defaultBranch
        )
    }
}

@Serializable
data class OwnerResponse(
    val login: String
)