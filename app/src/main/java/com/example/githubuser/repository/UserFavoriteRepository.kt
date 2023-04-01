package com.example.githubuser.repository

import android.content.Context
import android.util.Log
import com.example.githubuser.data.local.entity.FavoriteUser
import com.example.githubuser.data.local.room.UserRoom

class UserFavoriteRepository(context: Context){
    private val userDao = UserRoom.getInstance(context).userDao()

    fun getAllFavoriteUser() = userDao.getAllUser()

    fun insertFavoriteUser(user: FavoriteUser){
        val existingUser = userDao.getUserByUsername(user.username)
        Log.d("Favorite User", "insertFavoriteUser: $existingUser")
        if (existingUser == null) {
            userDao.insert(user)
        }
        else {
            Log.d("Favorite User", "User Already Exist")
        }
    }

    fun deleteFavoriteUser(username: String) = userDao.deleteUserByUsername(username)

    fun getUserByUsername(username: String) = userDao.getUserByUsername(username)

}