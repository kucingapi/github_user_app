package com.example.githubuser.repository

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class DataStoreRepository private constructor(private var context: Context) {
    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var instance: DataStoreRepository? = null

        fun getInstance(context: Context): DataStoreRepository =
            instance ?: synchronized(this) {
                instance ?: DataStoreRepository(context).also { instance = it }
            }

        val NIGHT_MODE = booleanPreferencesKey("night_mode")
    }
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    val readFromDataStore: Flow<Boolean> = context.dataStore.data.catch {
        if(it is IOException){
            Log.d("Repository Data Store", it.message.toString())
            emit(emptyPreferences())
        }
        else {
            throw it
        }
    }.map {
        it[NIGHT_MODE] ?: false
    }

    suspend fun saveToDataStore(nightMode: Boolean) {
        context.dataStore.edit {
            it[NIGHT_MODE] = nightMode
        }
    }
}