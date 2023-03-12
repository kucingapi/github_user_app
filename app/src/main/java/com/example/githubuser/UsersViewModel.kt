package com.example.githubuser

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.githubuser.api.ApiConfig.apiService
import com.example.githubuser.api.ResponseUsers
import com.example.githubuser.api.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UsersViewModel: ViewModel() {
    private val listUser = MutableLiveData<List<User>>()
    val _listUser: LiveData<List<User>> = listUser

    fun getUsers(username: String){
        val query = if(username.isEmpty()) "kucingapi" else username
        val client = apiService.getListUsers(query)

        client.enqueue(object : Callback<ResponseUsers> {
            override fun onResponse(call: Call<ResponseUsers>, response: Response<ResponseUsers>) {
                if (response.isSuccessful) {
                    listUser.value = response.body()?.users as List<User>
                    Log.d("response", "onResponse: $listUser")
                }
            }

            override fun onFailure(call: Call<ResponseUsers>, t: Throwable) {
                Log.d("response", "failed")
            }
        })
    }
}

//class UsersViewModelFactory() : ViewModelProvider.Factory {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        return super.create(modelClass)
//    }
//    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
//        return super.create(modelClass, extras)
//    }
//}

//class AuthViewModelFactory : ViewModelProvider.Factory {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T =
//        when {
//            modelClass.isAssignableFrom(UsersViewModel::class.java)->{
//                UsersViewModel() as T
//            }
//            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
//        }
//}