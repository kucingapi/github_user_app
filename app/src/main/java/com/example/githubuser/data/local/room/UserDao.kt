package com.example.githubuser.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.githubuser.data.local.entity.FavoriteUser

@Dao
interface UserDao {
    @Query("SELECT * FROM favorite_users")
    fun getAllUser(): List<FavoriteUser>

    @Query("SELECT * FROM favorite_users WHERE username = :username")
    fun getUserByUsername(username: String): FavoriteUser?

    @Insert
    fun insert(users: FavoriteUser)

    @Query("DELETE FROM favorite_users WHERE username = :username")
    fun deleteUserByUsername(username: String)
}