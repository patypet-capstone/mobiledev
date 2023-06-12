package com.ahmrh.patypet.data.remote.retrofit

import com.ahmrh.patypet.data.remote.responses.PetResponse
import com.ahmrh.patypet.data.remote.responses.PredictionResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface PetApiService {

    @Multipart
    @POST("upload")
    fun predict(
        @Part imgFile: MultipartBody.Part,
    ): Call<PredictionResponse>

    @GET("pet/{email}")
    fun getPetByEmail(
        @Path("email") email: String
    ): Call<PetResponse>
}