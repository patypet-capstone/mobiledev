package com.ahmrh.patypet.data.repositories


import android.util.Log
import com.ahmrh.patypet.data.local.AppPreferences
import com.ahmrh.patypet.data.remote.responses.LoginResponse
import com.ahmrh.patypet.data.remote.responses.RegisterResponse
import com.ahmrh.patypet.data.remote.responses.RemoteResponse
import com.ahmrh.patypet.data.remote.retrofit.AuthApiService
import com.google.gson.JsonObject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthRepository(
    private val apiService: AuthApiService,
    private val pref: AppPreferences
){
    init{
        println("AuthRepository created")
    }
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

     fun login(
        email: String,
        password: String,
        scope: CoroutineScope,
    ): Flow<String> = callbackFlow {

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
                    val token =
                        response.body()?.token.toString()
                    scope.launch {
                        pref.saveLogin(token)
                    }
                    trySend(response.body()?.name!!)
                } else {
                    Log.e(TAG, "onFailureResponse : ${response.message()}")
                    close(
                        Exception(response.message())
                    )
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
                close(
                    Exception(t.message)
                )

            }
        })

         awaitClose { client.cancel() }
    }

     fun register(
        name: String,
        email: String,
        password: String
    ) : Flow<String> = callbackFlow {
         Log.d(TAG, "register user")

         val rawJsonObject = JsonObject()
         rawJsonObject.addProperty("name", name)
         rawJsonObject.addProperty("email", email)
         rawJsonObject.addProperty("password", password)
         Log.d(TAG, rawJsonObject.toString())

        val client = apiService.register(rawJsonObject)
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
                    trySend(response.body()?.message!!)
                } else{

                    Log.e(TAG, "onFailureResponse : ${response.message()}")
                    close(
                        Exception(response.message())
                    )
                }
            }

            override fun onFailure(
                call: Call<RegisterResponse>,
                t: Throwable
            ) {
                Log.e(TAG, "onFailure : ${t.message}")
                close(
                    Exception(t.message)
                )
            }
        })

         awaitClose { client.cancel() }
    }


    companion object {
        const val TAG = "AuthRepository"
    }
}