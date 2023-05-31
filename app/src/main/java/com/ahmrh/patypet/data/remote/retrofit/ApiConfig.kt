package com.ahmrh.patypet.data.remote.retrofit

import com.ahmrh.patypet.BuildConfig
import com.ahmrh.patypet.data.local.AppPreferences
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig(private val pref: AppPreferences) {
    private val loggingInterceptor = if(BuildConfig.DEBUG) {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY ; HttpLoggingInterceptor.Level.HEADERS
        }
    } else {
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
    }
    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()


    fun getAuthApiService(): ApiService {

        val authURL = BuildConfig.AUTH_URL

        val retrofit = Retrofit.Builder()
            .baseUrl(authURL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        return retrofit.create(ApiService::class.java)
    }

    fun getPetApiService(): ApiService{

        val petUrl = BuildConfig.APP_URL

        val retrofit = Retrofit.Builder()
            .baseUrl(petUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        return retrofit.create(ApiService::class.java)
    }


    companion object{
        const val TAG = "ApiConfig"
    }
}