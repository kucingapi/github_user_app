package com.example.githubuser.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.githubuser.data.local.entity.FavoriteUser
import com.example.githubuser.repository.UserFavoriteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteUserViewModel(application: Application): ViewModel() {
    private val favoriteRepository: UserFavoriteRepository = UserFavoriteRepository(application)
    val favoriteUsers: MutableLiveData<List<FavoriteUser>> by lazy {
        MutableLiveData<List<FavoriteUser>>()
    }

    val loading: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }

    fun getAllFavoriteUser() = viewModelScope.launch(Dispatchers.IO){
        val allUser = favoriteRepository.getAllFavoriteUser()
        favoriteUsers.postValue(allUser)
    }
}

class FavoriteUserViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteUserViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FavoriteUserViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
