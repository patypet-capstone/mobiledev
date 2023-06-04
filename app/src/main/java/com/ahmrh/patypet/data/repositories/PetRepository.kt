package com.ahmrh.patypet.data.repositories

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import com.ahmrh.patypet.data.remote.responses.PredictionResponse
import com.ahmrh.patypet.data.remote.retrofit.PetApiService
import com.ahmrh.patypet.domain.utils.reduceFileImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class PetRepository(
    private val apiService: PetApiService,
) {
    init {
        println("PetRepository created")
    }

    fun predict(
        imgFile: File
    ): Flow<PredictionResponse> = flow{
        var predictionResponse: PredictionResponse = PredictionResponse()
        val file = reduceFileImage(imgFile)

        val requestImageFile =
            file.asRequestBody("image/jpeg".toMediaType())

        val imageMultipart: MultipartBody.Part =
            MultipartBody.Part.createFormData(
                "imgFile",
                file.name,
                requestImageFile
            )

        val uploadImageRequest = apiService.predict(imageMultipart)
        try{

            uploadImageRequest.enqueue(object : Callback<PredictionResponse> {
                override fun onResponse(
                    call: Call<PredictionResponse>,
                    response: Response<PredictionResponse>,
                ) {
                    if (response.isSuccessful) {
                        predictionResponse = response.body()!!
                    } else {

                        Log.e(
                            AuthRepository.TAG,
                            "onFailureResponse : ${response.message()}"
                        )
                        throw Exception(response.message())

                    }
                }

                override fun onFailure(
                    call: Call<PredictionResponse>,
                    t: Throwable
                ) {
                    Log.e(
                        AuthRepository.TAG,
                        "onFailureThrowable: ${t.message}"
                    )
                    throw Exception(t.message)
                }
            })

        } finally{
            emit(predictionResponse)
        }



    }
}