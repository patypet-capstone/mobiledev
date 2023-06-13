package com.ahmrh.patypet.di

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.ContextWrapper
import androidx.datastore.preferences.preferencesDataStore
import com.ahmrh.patypet.data.local.AppPreferences
import com.ahmrh.patypet.data.remote.retrofit.ApiConfig
import com.ahmrh.patypet.data.repositories.AuthRepository
import com.ahmrh.patypet.data.repositories.PetRepository

object Injection {
    private var application: Application? = null

    private inline val requireApplication
        get() = application ?: error("Missing: Injection Application")

    fun init(application: Application) {
        this.application = application
    }

    private val Context.dataStore by preferencesDataStore(name = "app_preferences")
    fun Context.findActivity(): Activity {
        var context = this
        while (context is ContextWrapper) {
            if (context is Activity) return context
            context = context.baseContext
        }
        throw IllegalStateException("no activity")
    }
    fun provideAuthRepository(context: Context): AuthRepository {
        val pref = AppPreferences.getInstance(
            requireApplication.dataStore)
        return AuthRepository(ApiConfig(pref).getAuthApiService(), pref)
    }

    fun providePetRepository(context: Context): PetRepository {
        val pref = AppPreferences.getInstance(
            requireApplication.dataStore)
        return PetRepository(ApiConfig(pref).getPetApiService(), ApiConfig(pref).getAuthApiService())
    }

}