package com.ahmrh.patypet.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.preferences.preferencesDataStoreFile
import com.ahmrh.patypet.BuildConfig
import com.ahmrh.patypet.data.local.AppPreferences
import com.ahmrh.patypet.data.remote.retrofit.AuthApiService
import com.ahmrh.patypet.data.remote.retrofit.PetApiService
import com.ahmrh.patypet.data.repositories.AuthRepository
import com.ahmrh.patypet.data.repositories.PetRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class) // how long the dependency will actually live, singleton mean as long as the app live (another one: viewmodel, activity, etc)
object AppModule {
    @Provides
    @Singleton
    fun provideAuthRepository(apiService: AuthApiService, pref: AppPreferences ): AuthRepository{
        return AuthRepository(apiService, pref)
    }

    @Provides
    @Singleton
    fun providePetRepository(apiService: PetApiService, pref: AppPreferences): PetRepository{
        return PetRepository(apiService)
    }

    @Singleton
    @Provides
    fun providePreferencesDataStore(@ApplicationContext appContext: Context): AppPreferences {
        return AppPreferences.getInstance(appContext.dataStore)
    }

    @Provides
    @Singleton
    fun provideAuthApiService(): AuthApiService {
        val loggingInterceptor = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor().apply {
                level =
                    HttpLoggingInterceptor.Level.BODY; HttpLoggingInterceptor.Level.HEADERS
            }
        } else {
            HttpLoggingInterceptor().setLevel(
                HttpLoggingInterceptor.Level.NONE
            )
        }
        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        val authURL = BuildConfig.HTTP_AUTH_URL

        val retrofit = Retrofit.Builder()
            .baseUrl(authURL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        return retrofit.create(AuthApiService::class.java)
    }

    @Provides
    @Singleton
    fun providePetApiService(): PetApiService {
        val loggingInterceptor = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor().apply {
                level =
//                    HttpLoggingInterceptor.Level.BODY; HttpLoggingInterceptor.Level.HEADERS
                    HttpLoggingInterceptor.Level.NONE
            }
        } else {
            HttpLoggingInterceptor().setLevel(
                HttpLoggingInterceptor.Level.NONE
            )
        }
        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        val appURL = BuildConfig.HTTP_APP_URL

        val retrofit = Retrofit.Builder()
            .baseUrl(appURL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        return retrofit.create(PetApiService::class.java)
    }

}