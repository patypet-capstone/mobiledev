package com.ahmrh.patypet.data.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ahmrh.patypet.data.local.AppPreferences
import com.ahmrh.patypet.data.remote.responses.LoginResponse
import com.ahmrh.patypet.data.remote.responses.RegisterResponse
import com.ahmrh.patypet.data.remote.retrofit.ApiService
import com.ahmrh.patypet.data.model.Login
import com.ahmrh.patypet.data.model.RemoteResponse
import com.ahmrh.patypet.ui.screen.auth.AuthViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthRepository(
    private val apiService: ApiService,
    private val pref: AppPreferences
) {

    fun login(
        email: String,
        password: String,
        viewModelScope: CoroutineScope
    ): LiveData<RemoteResponse> {
        val loginResponseLiveData =
            MutableLiveData<RemoteResponse>()

        val client = apiService.login(email, password)
        client.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                if(response.isSuccessful) {
                    loginResponseLiveData.value =
                        RemoteResponse(
                            success = true,
                            message = response.message()
                        )

                    val name =
                        response.body()?.name.toString()
                    val token =
                        response.body()?.token.toString()
                    viewModelScope.launch{
                        pref.saveLogin(
                            Login(name, token)
                        )
                    }
                }
                else {
                    loginResponseLiveData.value =
                        RemoteResponse(
                            success = false,
                            message = response.message()
                        )
                    Log.e(TAG, "onFailureResponse ")
                }

            }

            override fun onFailure(
                call: Call<LoginResponse>,
                t: Throwable
            ) {
                loginResponseLiveData.value =
                    RemoteResponse(
                        success = false,
                        message = t.message ?: "Unidentified Failure"
                    )
                Log.e(TAG, "onFailureThrowable: ${t.message}"
                )
            }
        })

        return loginResponseLiveData

    }

    fun register(
        name: String,
        email: String,
        password: String
    ): LiveData<RemoteResponse> {
        val registerResponseLiveData =
            MutableLiveData<RemoteResponse>()
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
                    registerResponseLiveData.value =
                        RemoteResponse(
                            success = true,
                            message = response.message()
                        )

                } else {
                    registerResponseLiveData.value =
                    RemoteResponse(
                        success = false,
                        message = response.message()
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
                registerResponseLiveData.value =
                RemoteResponse(
                    success = false,
                    message = t.message ?: "Unidentified Failure"
                )
                Log.e(
                    TAG,
                    "onFailureThrowable: ${t.message}"
                )
            }
        })

        return registerResponseLiveData
    }

    companion object {
        const val TAG = "AuthRepository"
    }
}