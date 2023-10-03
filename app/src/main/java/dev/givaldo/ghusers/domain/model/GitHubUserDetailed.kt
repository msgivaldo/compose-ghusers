package dev.givaldo.ghusers.domain.model

data class GitHubUserDetailed(
    val login: String,
    val id: Long,
    val avatarUrl: String,
    val url: String,
    val type: String,
    val name: String,
    val company: String?,
    val blog: String?,
    val location: String?,
    val email: String?,
    val hireable: Boolean,
    val bio: String?,
    val twitterUsername: String?,
    val publicRepos: Int,
    val publicGists: Int,
    val followers: Int,
    val following: Int,
    val createdAt: String,
    val updatedAt: String
) {

    val properties
        get() = listOf(
            "login" to login,
            "id" to id,
            "avatarUrl" to avatarUrl,
            "url" to url,
            "type" to type,
            "name" to name,
            "company" to company,
            "blog" to blog,
            "location" to location,
            "email" to email,
            "hireable" to hireable,
            "bio" to bio,
            "twitterUsername" to twitterUsername,
            "publicRepos" to publicRepos,
            "publicGists" to publicGists,
            "followers" to followers,
            "following" to following,
        )
}