package com.example.githubuser.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser.api.ApiConfig.apiService
import com.example.githubuser.api.ResponseDetailUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserDetailViewModel: ViewModel() {
    val userDetail: MutableLiveData<ResponseDetailUser> by lazy {
        MutableLiveData<ResponseDetailUser>()
    }

    val loading: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }

    fun getUserDetail(username: String){
        val query = username.ifEmpty { "kucingapi" }
        val client = apiService.getUserDetail(query)
        loading.value = true
        client.enqueue(object : Callback<ResponseDetailUser> {
            override fun onResponse(call: Call<ResponseDetailUser>, response: Response<ResponseDetailUser>) {
                if (response.isSuccessful) {
                    userDetail.value = response.body() as ResponseDetailUser
                    loading.value = false
                }
            }

            override fun onFailure(call: Call<ResponseDetailUser>, t: Throwable) {
                Log.d("response", "failed")
                loading.value = false
            }
        })
    }

}