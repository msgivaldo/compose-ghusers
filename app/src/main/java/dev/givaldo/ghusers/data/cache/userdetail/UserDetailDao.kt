package dev.givaldo.ghusers.data.cache.userdetail

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDetailDao {

    @Query("SELECT * FROM userDetail WHERE login = :login LIMIT 1")
    fun getByUsername(login: String): Flow<UserDetailEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(usersDetail: UserDetailEntity)

    @Delete
    fun delete(usersDetail: UserDetailEntity)
}