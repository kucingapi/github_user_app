package com.example.githubuser

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser.api.ApiConfig.apiService
import com.example.githubuser.api.ResponseUsers
import com.example.githubuser.api.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UsersViewModel: ViewModel() {
    private val listUser = MutableLiveData<List<User>>()
    val _listUser: LiveData<List<User>> = listUser
    val loading: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }

    fun getUsers(username: String){
        val query = if(username.isEmpty()) "kucingapi" else username
        val client = apiService.getListUsers(query)
        loading.value = true
        client.enqueue(object : Callback<ResponseUsers> {
            override fun onResponse(call: Call<ResponseUsers>, response: Response<ResponseUsers>) {
                if (response.isSuccessful) {
                    listUser.value = response.body()?.users as List<User>
                    loading.value = false
                }
            }

            override fun onFailure(call: Call<ResponseUsers>, t: Throwable) {
                Log.d("response", "failed")
                loading.value = false
            }
        })
    }
}
