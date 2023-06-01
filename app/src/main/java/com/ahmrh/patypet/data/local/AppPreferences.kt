package com.ahmrh.patypet.data.local

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.ahmrh.patypet.data.model.User
import com.ahmrh.patypet.ui.screen.auth.AuthViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

class AppPreferences(private val dataStore: DataStore<Preferences>) {
    companion object {
        const val TAG = "AppPreferences"
        private val TOKEN_KEY = stringPreferencesKey("token")

        @Volatile
        private var INSTANCE: AppPreferences? = null

        fun getInstance(dataStore: DataStore<Preferences>): AppPreferences {
            return INSTANCE ?: synchronized(this) {
                val instance = AppPreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }

     suspend fun saveLogin(token: String){
         dataStore.edit {preferences ->
             preferences[TOKEN_KEY] = token
             Log.d(TAG, "SaveLogin:  $token ")
         }
    }

    suspend fun deleteLogin(){
        Log.d(AuthViewModel.TAG, "delete login")
        dataStore.edit { preferences ->
            preferences[TOKEN_KEY] = ""
        }
    }

    fun isLogin() : Flow<Boolean> {
        return dataStore.data.map { preferencees ->
            preferencees[TOKEN_KEY] != ""
        }
    }

    fun getToken() = runBlocking {
        dataStore.data.first()[TOKEN_KEY]
    }

}