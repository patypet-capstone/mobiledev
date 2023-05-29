package com.ahmrh.patypet.di

import android.app.Application
import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import com.ahmrh.patypet.data.local.AppPreferences
import com.ahmrh.patypet.data.remote.retrofit.ApiConfig
import com.ahmrh.patypet.data.repositories.AuthRepository

object Injection {
    private var application: Application? = null

    private inline val requireApplication
        get() = application ?: error("Missing: Injection Application")

    fun init(application: Application) {
        this.application = application
    }

    private val Context.dataStore by preferencesDataStore(name = "app_preferences")

    fun provideAuthRepository(context: Context): AuthRepository {
        val pref = AppPreferences.getInstance(
            requireApplication.dataStore)
        return AuthRepository(ApiConfig(pref).getApiService(), pref)
    }

}