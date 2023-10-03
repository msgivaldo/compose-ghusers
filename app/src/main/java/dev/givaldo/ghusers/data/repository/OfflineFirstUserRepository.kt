package dev.givaldo.ghusers.data.repository

import dev.givaldo.ghusers.data.cache.GHUsersDatabase
import dev.givaldo.ghusers.data.cache.repo.RepoDao
import dev.givaldo.ghusers.data.cache.repo.asEntity
import dev.givaldo.ghusers.data.cache.user.UserDao
import dev.givaldo.ghusers.data.cache.user.asEntity
import dev.givaldo.ghusers.data.cache.userdetail.UserDetailDao
import dev.givaldo.ghusers.data.cache.userdetail.asEntity
import dev.givaldo.ghusers.data.network.GHUsersNetworkApi
import dev.givaldo.ghusers.data.network.KtorGHUsersNetworkApi
import dev.givaldo.ghusers.domain.model.GitHubUserDetailed
import dev.givaldo.ghusers.domain.model.GithubRepo
import dev.givaldo.ghusers.domain.model.GithubUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map

class OfflineFirstUserRepository(
    private val userDao: UserDao = GHUsersDatabase.database.userDao(),
    private val repoDao: RepoDao = GHUsersDatabase.database.repoDao(),
    private val userDetailDao: UserDetailDao = GHUsersDatabase.database.userDetailDao(),
    private val network: GHUsersNetworkApi = KtorGHUsersNetworkApi()
) : UserRepository, Syncable {

    override fun fetchUsers(): Flow<List<GithubUser>> {
        return userDao.getAll()
            .onStartAsync {
                val remote = network.getUsers().map { it.toDomain() }
                userDao.insertAll(remote.map { it.asEntity() })
            }.map { list ->
                list.map { it.asDomain() }
            }
            .distinctUntilChanged()
    }

    override fun fetchUserDetail(username: String): Flow<GitHubUserDetailed> {
        return userDetailDao.getByUsername(username)
            .filterNotNull()
            .onStartAsync {
                val remote = network.getUserDetail(username).toDomain()
                userDetailDao.insert(remote.asEntity())
            }.map { entity ->
                entity.asDomain()
            }
            .distinctUntilChanged()
    }

    override fun fetchUserRepos(username: String): Flow<List<GithubRepo>> {
        return repoDao.getByUser(username)
            .onStartAsync {
                val remote = network.getUserRepos(username).map { it.toDomain() }
                repoDao.insertAll(remote.map { it.asEntity() })
            }.map { list ->
                list.map { it.asDomain() }
            }
            .distinctUntilChanged()

    }
}