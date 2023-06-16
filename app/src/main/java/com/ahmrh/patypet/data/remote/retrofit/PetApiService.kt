package com.ahmrh.patypet.data.remote.retrofit

import com.ahmrh.patypet.data.remote.responses.ChangeNameResponse
import com.ahmrh.patypet.data.remote.responses.PetByIdResponse
import com.ahmrh.patypet.data.remote.responses.PetResponse
import com.ahmrh.patypet.data.remote.responses.PredictionResponse
import com.ahmrh.patypet.data.remote.responses.SaveResponse
import com.ahmrh.patypet.data.remote.responses.ShopResponseItem
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface PetApiService {
    @Multipart
    @POST("upload")
    fun predict(
        @Part imgFile: MultipartBody.Part,
    ): Call<PredictionResponse>



    @GET("shop/{product}/{jenis}")
    fun getShopProduct(
        @Path("product") product: String,
        @Path("jenis") jenis: String
    ) : Call<List<ShopResponseItem>>

    @POST("save")
    fun savePet(): Call<SaveResponse>

    @PUT("pet/edit")
    fun changeName(
        @Query("id") id : Int,
        @Query("name") name: String
    ): Call<ChangeNameResponse>

    @GET("pet/{email}")
    fun getPetByEmail(
        @Path("email") email: String
    ): Call<PetResponse>

    @GET("pet/{email}/{id}")
    fun getPetByEmailAndId(
        @Path("email") email: String,
        @Path("id") id: Int,
    ): Call<PetByIdResponse>


}