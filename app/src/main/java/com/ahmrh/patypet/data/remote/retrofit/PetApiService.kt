package com.ahmrh.patypet.data.remote.retrofit

import com.ahmrh.patypet.data.remote.responses.PredictionResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface PetApiService {

    @FormUrlEncoded
    @POST("predict")
    fun predict(
        @Field("email") email: String,
        @Field("password") password: String,
    ): Call<PredictionResponse>
}