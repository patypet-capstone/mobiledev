package com.ahmrh.patypet.data.repositories

import android.util.Log
import com.ahmrh.patypet.data.local.AppPreferences
import com.ahmrh.patypet.data.remote.responses.RemoteResponse
import com.ahmrh.patypet.data.model.User
import com.ahmrh.patypet.data.remote.responses.LoginResponse
import com.ahmrh.patypet.data.remote.responses.RegisterResponse
import com.ahmrh.patypet.data.remote.responses.UserResponse
import com.ahmrh.patypet.data.remote.retrofit.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
                throw(t)
            }
        })

        return user
    }


    suspend fun login(
        email: String,
        password: String,
    ): Flow<RemoteResponse> {
        var token = ""

        val client = apiService.login(email, password)
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
                throw(t)
            }
        })

        pref.saveLogin(token)

        return authResponse
    }

     fun register(
        name: String,
        email: String,
        password: String
    ): Flow<RemoteResponse> {
        val client =
            apiService.register(name, email, password)
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
                throw(t)
            }
        })

        return authResponse
    }

    companion object {
        const val TAG = "AuthRepository"
    }
}