package dev.givaldo.ghusers.data.cache.repo

import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.givaldo.ghusers.domain.model.GithubRepo

@Entity(tableName = "repo")
data class RepoEntity(
    @PrimaryKey val id: Int,
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
) {
    fun asDomain(): GithubRepo {
        return GithubRepo(
            id = id,
            login = login,
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

fun GithubRepo.asEntity(): RepoEntity {
    return RepoEntity(
        id = id,
        login = login,
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
