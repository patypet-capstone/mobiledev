package com.ahmrh.patypet.data.remote.retrofit

import com.ahmrh.patypet.data.remote.responses.LoginResponse
import com.ahmrh.patypet.data.remote.responses.RegisterResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {
    @FormUrlEncoded
    @POST("register")
    fun register(
        @Field("name") name:String,
        @Field("email") email:String,
        @Field("password") password:String
    ): Call<RegisterResponse>

    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("email") email:String,
        @Field("password") password: String
    ): Call<LoginResponse>

    @FormUrlEncoded
    @POST("predict")
    fun predict(
        @Field("email") email: String,
        @Field("password") password: String,
    )

}