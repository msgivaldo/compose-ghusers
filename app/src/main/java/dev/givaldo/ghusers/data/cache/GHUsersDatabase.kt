package dev.givaldo.ghusers.data.cache

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dev.givaldo.ghusers.GHUsersApplication.Companion.applicationContext
import dev.givaldo.ghusers.data.cache.repo.RepoDao
import dev.givaldo.ghusers.data.cache.repo.RepoEntity
import dev.givaldo.ghusers.data.cache.user.UserDao
import dev.givaldo.ghusers.data.cache.user.UserEntity
import dev.givaldo.ghusers.data.cache.userdetail.UserDetailDao
import dev.givaldo.ghusers.data.cache.userdetail.UserDetailEntity

@Database(entities = [UserEntity::class, RepoEntity::class, UserDetailEntity::class], version = 1)
abstract class GHUsersDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun repoDao(): RepoDao
    abstract fun userDetailDao(): UserDetailDao

    companion object {
        val database = Room.databaseBuilder(
            applicationContext,
            GHUsersDatabase::class.java, "ghusers-database"
        ).build()
    }
}