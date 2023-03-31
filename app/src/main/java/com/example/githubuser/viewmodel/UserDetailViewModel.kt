package com.example.githubuser.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.githubuser.api.ApiConfig.apiService
import com.example.githubuser.api.ResponseDetailUser
import com.example.githubuser.data.local.entity.FavoriteUser
import com.example.githubuser.repository.UserFavoriteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserDetailViewModel(application: Application): ViewModel() {
    private val userFavoriteRepository = UserFavoriteRepository(application)

    val userDetail: MutableLiveData<ResponseDetailUser> by lazy {
        MutableLiveData<ResponseDetailUser>()
    }

    val isFavorite: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    val loading: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }

    fun insertFavoriteUser(user: FavoriteUser) = viewModelScope.launch(Dispatchers.IO) {
        userFavoriteRepository.insertFavoriteUser(user)
        isFavorite.postValue(true)
    }
    fun isUserFavorite(username: String) = viewModelScope.launch(Dispatchers.IO) {
        val user = userFavoriteRepository.getUserByUsername(username)
        isFavorite.postValue(user != null)
    }
    fun deleteFavoriteUser(username: String) = viewModelScope.launch(Dispatchers.IO) {
        userFavoriteRepository.deleteFavoriteUser(username)
        isFavorite.postValue(false)
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

class UserDetailViewModelFactory(private val application: Application) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserDetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UserDetailViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
