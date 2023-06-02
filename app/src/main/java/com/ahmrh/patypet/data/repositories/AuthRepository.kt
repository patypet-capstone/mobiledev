package com.ahmrh.patypet.data.repositories

import android.util.Log
import com.ahmrh.patypet.data.local.AppPreferences
import com.ahmrh.patypet.domain.model.User
import com.ahmrh.patypet.data.remote.responses.LoginResponse
import com.ahmrh.patypet.data.remote.responses.RegisterResponse
import com.ahmrh.patypet.data.remote.responses.RemoteResponse
import com.ahmrh.patypet.data.remote.responses.UserResponse
import com.ahmrh.patypet.data.remote.retrofit.ApiService
import com.google.gson.JsonObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class AuthRepository(
    private val apiService: ApiService,
    private val pref: AppPreferences
) {
    private var authResponse = MutableStateFlow(
        RemoteResponse(
            success = false,
            message = "Not Initialized"
        )
    )

    fun isLogin(): Flow<Boolean> = pref.isLogin()
    fun getToken(): String? = pref.getToken()

    suspend fun endSession() = pref.deleteLogin()

    fun forceLogin(
        scope: CoroutineScope
    ){
        scope.launch{
            pref.saveLogin("temporary token")
        }

    }

    fun getUser(
        token: String
    ): Flow<User> {

        val user = MutableStateFlow(User())
        
        val client = apiService.getUser(token)
        client.enqueue(object : Callback<UserResponse> {
            override fun onResponse(
                call: Call<UserResponse>,
                response: Response<UserResponse>
            ) {
                if (response.isSuccessful) {
                    user.value  = User(
                        response.body()?.id ?: -1,
                        response.body()?.name ?: "Unnamed Person",
                        response.body()?.email ?: "Null",
                    )

                } else {
                    Log.e(TAG, "onFailureResponse: ${response.message()}")
                }

            }

            override fun onFailure(
                call: Call<UserResponse>,
                t: Throwable
            ) {
                Log.e(
                    TAG,
                    "onFailureThrowable: ${t.message}"
                )

            }
        })

        return user
    }


    suspend fun login(
        email: String,
        password: String,
        scope: CoroutineScope,
    ): Flow<RemoteResponse> {
        var token = ""

        val rawJsonObject = JsonObject()
        rawJsonObject.addProperty("email", email)
        rawJsonObject.addProperty("password", password)
        Log.d(TAG, rawJsonObject.toString())

        val client = apiService.login(rawJsonObject)
        client.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                if (response.isSuccessful) {
                    token =
                        response.body()?.token.toString()
                    authResponse.value =
                        RemoteResponse(
                            success = true,
                            message = response.message(),
                        )
                    scope.launch {
                        pref.saveLogin(token)
                    }
                } else {
                    authResponse.value =
                        RemoteResponse(
                            success = false,
                            message = response.message(),
                        )
                    Log.e(TAG, "onFailureResponse ")
                }

            }

            override fun onFailure(
                call: Call<LoginResponse>,
                t: Throwable
            ) {
                Log.e(
                    TAG,
                    "onFailureThrowable: ${t.message}"
                )

            }
        })

        return authResponse
    }

     fun register(
        name: String,
        email: String,
        password: String
    ): Flow<RemoteResponse> {

         val rawJsonObject = JsonObject()
         rawJsonObject.addProperty("name", name)
         rawJsonObject.addProperty("email", email)
         rawJsonObject.addProperty("password", password)
         Log.d(TAG, rawJsonObject.toString())

        val client =
            apiService.register(rawJsonObject)
        client.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                Log.e(
                    TAG,
                    response.body().toString()
                )
                if (response.isSuccessful) {
                    authResponse.value =
                        RemoteResponse(
                            success = true,
                            message = response.message(),
                        )

                } else {
                    authResponse.value =
                        RemoteResponse(
                            success = false,
                            message = response.message(),
                        )
                    Log.e(
                        TAG,
                        "onFailureResponse: ${response.message()}"
                    )
                }
            }

            override fun onFailure(
                call: Call<RegisterResponse>,
                t: Throwable
            ) {
                Log.e(
                    TAG,
                    "onFailureThrowable: ${t.message}"
                )
            }
        })

        return authResponse
    }

    companion object {
        const val TAG = "AuthRepository"
    }
}