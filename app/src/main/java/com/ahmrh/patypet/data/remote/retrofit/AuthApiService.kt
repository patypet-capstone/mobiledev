package com.ahmrh.patypet.data.remote.retrofit

import com.ahmrh.patypet.data.remote.responses.ArticleResponse
import com.ahmrh.patypet.data.remote.responses.ArticleResponseItem
import com.ahmrh.patypet.data.remote.responses.LoginResponse
import com.ahmrh.patypet.data.remote.responses.PredictionResponse
import com.ahmrh.patypet.data.remote.responses.RegisterResponse
import com.ahmrh.patypet.data.remote.responses.UserResponse
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface AuthApiService {
    @Headers("Content-Type: application/json")
    @POST("register")
    fun register(
        @Body rawJsonObject: JsonObject,
    ): Call<RegisterResponse>

    @Headers("Content-Type: application/json")
    @POST("login")
    fun login(
        @Body rawJsonObject: JsonObject,
    ): Call<LoginResponse>


    @FormUrlEncoded
    @POST("user")
    fun getUser(
        @Header("Authorization") token: String,
    ): Call<UserResponse>

    @GET("articles")
    fun fetchArticle(
        @Query("jenis") jenis: String = "",
    ) : Call<List<ArticleResponseItem>>

}