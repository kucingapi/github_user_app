package com.example.githubuser.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.githubuser.api.ApiConfig.apiService
import com.example.githubuser.api.ResponseUsers
import com.example.githubuser.api.User
import com.example.githubuser.data.local.entity.FavoriteUser
import com.example.githubuser.repository.DataStoreRepository
import com.example.githubuser.repository.UserFavoriteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UsersViewModel(application: Application, private val repository: DataStoreRepository): AndroidViewModel(application) {
    private val listUser = MutableLiveData<List<User>>()
    val _listUser: LiveData<List<User>> = listUser
    val loading: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }

    val readFromDataStore = repository.readFromDataStore.asLiveData()


    fun saveToDataStore(nightMode: Boolean) = viewModelScope.launch(Dispatchers.IO) {
        repository.saveToDataStore(nightMode)
    }

    fun getUsers(username: String){
        val query = if(username.isEmpty()) "kucingapi" else username
        val client = apiService.getListUsers(query)
        loading.value = true
        client.enqueue(object : Callback<ResponseUsers> {
            override fun onResponse(call: Call<ResponseUsers>, response: Response<ResponseUsers>) {
                if (response.isSuccessful) {
                    Log.d("response", "failed")
                    listUser.value = response.body()?.users as List<User>
                    loading.value = false
                }
            }

            override fun onFailure(call: Call<ResponseUsers>, t: Throwable) {
                Log.d("response", "${t.message}")
                loading.value = false
            }
        })
    }
}

class UsersViewModelFactory(private val application: Application, private val repository: DataStoreRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UsersViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UsersViewModel(application, repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
