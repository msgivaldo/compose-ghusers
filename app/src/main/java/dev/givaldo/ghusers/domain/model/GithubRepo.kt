package dev.givaldo.ghusers.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class GithubRepo(
    val id: Int,
    val login: String,
    val name: String,
    val fullName: String,
    val private: Boolean,
    val htmlUrl: String,
    val description: String?,
    val fork: Boolean,
    val url: String,
    val homepage: String?,
    val size: Int,
    val watchersCount: Int,
    val language: String?,
    val hasIssues: Boolean,
    val mirrorUrl: String?,
    val archived: Boolean,
    val disabled: Boolean,
    val openIssuesCount: Int,
    val allowForking: Boolean,
    val openIssues: Int,
    val watchers: Int,
    val defaultBranch: String
)
