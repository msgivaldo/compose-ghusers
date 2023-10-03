package dev.givaldo.ghusers.data.repository

import dev.givaldo.ghusers.domain.model.GitHubUserDetailed
import dev.givaldo.ghusers.domain.model.GithubRepo
import dev.givaldo.ghusers.domain.model.GithubUser
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun fetchUsers(): Flow<List<GithubUser>>
    fun fetchUserDetail(username: String): Flow<GitHubUserDetailed>
    fun fetchUserRepos(username: String): Flow<List<GithubRepo>>
}
