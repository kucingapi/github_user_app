package com.example.githubuser.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser.api.ApiConfig.apiService
import com.example.githubuser.api.ResponseFollow
import com.example.githubuser.api.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowersViewModel: ViewModel() {
    val userFollowers: MutableLiveData<List<User>> by lazy {
        MutableLiveData<List<User>>()
    }
    val loading: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }

    fun getFollowers(username: String){
        val query = username.ifEmpty { "kucingapi" }
        val client = apiService.getFollowers(query)
        loading.value = true
        client.enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (response.isSuccessful) {
                    userFollowers.value = response.body()
                    loading.value = false
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                Log.d("response", "${t.message}")
                loading.value = false
            }
        })
    }

}