package com.ahmrh.patypet.data.remote.retrofit

import com.ahmrh.patypet.BuildConfig
import com.ahmrh.patypet.data.local.AppPreferences
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

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

        val authURL = BuildConfig.HTTP_AUTH_URL

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

fun unSafeOkHttpClient() :OkHttpClient.Builder {
    val okHttpClient = OkHttpClient.Builder()
    try {
        // Create a trust manager that does not validate certificate chains
        val trustAllCerts:  Array<TrustManager> = arrayOf(object :
                                                              X509TrustManager {
            override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?){}
            override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {}
            override fun getAcceptedIssuers(): Array<X509Certificate>  = arrayOf()
        })

        // Install the all-trusting trust manager
        val  sslContext = SSLContext.getInstance("SSL")
        sslContext.init(null, trustAllCerts, SecureRandom())

        // Create an ssl socket factory with our all-trusting manager
        val sslSocketFactory = sslContext.socketFactory
        if (trustAllCerts.isNotEmpty() &&  trustAllCerts.first() is X509TrustManager) {
            okHttpClient.sslSocketFactory(sslSocketFactory, trustAllCerts.first() as X509TrustManager)
            okHttpClient.hostnameVerifier { _, _ -> true } // change here
        }

        return okHttpClient
    } catch (e: Exception) {
        return okHttpClient
    }
}