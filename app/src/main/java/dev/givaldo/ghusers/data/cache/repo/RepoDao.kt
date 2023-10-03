package dev.givaldo.ghusers.data.cache.repo

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface RepoDao {

    @Query("SELECT * FROM repo WHERE login = :login")
    fun getByUser(login: String): Flow<List<RepoEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(repos: List<RepoEntity>)

    @Delete
    fun delete(repo: RepoEntity)
}