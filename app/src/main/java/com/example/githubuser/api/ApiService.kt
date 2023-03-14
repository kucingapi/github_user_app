package com.example.githubuser.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    fun getListUsers(@Query("q") username: String): Call<ResponseUsers>
    @GET("users/{username}")
    fun getUserDetail(@Path("username") username: String): Call<ResponseDetailUser>
}