package dev.givaldo.ghusers.data.cache.userdetail

import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.givaldo.ghusers.domain.model.GitHubUserDetailed

@Entity(tableName = "userDetail")
class UserDetailEntity(
    @PrimaryKey val id: Long,
    val login: String,
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

    fun asDomain(): GitHubUserDetailed {
        return GitHubUserDetailed(
            login = login,
            id = id,
            avatarUrl = avatarUrl,
            url = url,
            type = type,
            name = name,
            company = company,
            blog = blog,
            location = location,
            email = email,
            hireable = hireable,
            bio = bio,
            twitterUsername = twitterUsername,
            publicRepos = publicRepos,
            publicGists = publicGists,
            followers = followers,
            following = following,
            createdAt = createdAt,
            updatedAt = updatedAt
        )
    }
}

fun GitHubUserDetailed.asEntity(): UserDetailEntity {
    return UserDetailEntity(
        login = login,
        id = id,
        avatarUrl = avatarUrl,
        url = url,
        type = type,
        name = name,
        company = company,
        blog = blog,
        location = location,
        email = email,
        hireable = hireable,
        bio = bio,
        twitterUsername = twitterUsername,
        publicRepos = publicRepos,
        publicGists = publicGists,
        followers = followers,
        following = following,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}