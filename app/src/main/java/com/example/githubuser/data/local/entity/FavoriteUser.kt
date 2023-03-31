package com.example.githubuser.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="favorite_users")
data class FavoriteUser(
    @PrimaryKey val username: String,
    @ColumnInfo(name = "profile_url") val profilePicture: String,
)
