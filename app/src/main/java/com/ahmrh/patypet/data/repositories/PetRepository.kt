package com.ahmrh.patypet.data.repositories

import android.util.Log
import com.ahmrh.patypet.data.remote.responses.ArticleResponse
import com.ahmrh.patypet.data.remote.responses.PetResponse
import com.ahmrh.patypet.data.remote.responses.PredictionResponse
import com.ahmrh.patypet.data.remote.retrofit.AuthApiService
import com.ahmrh.patypet.data.remote.retrofit.PetApiService
import com.ahmrh.patypet.domain.utils.reduceFileImage
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class PetRepository(
    private val petApiService: PetApiService,
    private val authApiService: AuthApiService
) {
    companion object{
        const val TAG = "Pet Repository"
    }
    init {
        println("PetRepository created")
    }

    fun predict(
        imgFile: File
    ): Flow<PredictionResponse> = callbackFlow {
        val file = reduceFileImage(imgFile)

        val requestImageFile =
            file.asRequestBody("image/jpeg".toMediaType())

        val imageMultipart: MultipartBody.Part =
            MultipartBody.Part.createFormData(
                "imgFile",
                file.name,
                requestImageFile
            )

        val uploadImageRequest = petApiService.predict(imageMultipart)
        uploadImageRequest.enqueue(object : Callback<PredictionResponse> {
            override fun onResponse(
                call: Call<PredictionResponse>,
                response: Response<PredictionResponse>,
            ) {
                if (response.isSuccessful) {
                    val predictionResponse = response.body()!!
                    Log.d(TAG, predictionResponse.toString())
                    trySend(predictionResponse) // Emit the response value
                    close() // Close the flow after emitting the value
                } else {

                    Log.e(
                        TAG,
                        "onFailureResponse : ${response.message()}"
                    )
                    close(
                        Exception(response.message())
                    ) // Close the flow with an exception

                }
            }

            override fun onFailure(
                call: Call<PredictionResponse>,
                t: Throwable
            ) {
                Log.e(
                    TAG,
                    "onFailureThrowable: ${t.message}"
                )
                close(Exception(t.message)) // Close the flow with an exception
            }
        })

        awaitClose { uploadImageRequest.cancel() }
    }

    fun fetchArticle(jenis: String?) : Flow<ArticleResponse> = callbackFlow {

        val client = authApiService.fetchArticle(jenis ?: "")
        client.enqueue(object : Callback<ArticleResponse> {
            override fun onResponse(
                call: Call<ArticleResponse>,
                response: Response<ArticleResponse>,
            ) {
                if (response.isSuccessful) {
                    val articleResponse = response.body()!!
                    Log.d(TAG, articleResponse.toString())
                    trySend(articleResponse) // Emit the response value
                    close() // Close the flow after emitting the value

                } else {

                    Log.e(
                        TAG,
                        "onFailureResponse : ${response.message()}"
                    )
                    close(
                        Exception(response.message())
                    ) // Close the flow with an exception

                }
            }

            override fun onFailure(
                call: Call<ArticleResponse>,
                t: Throwable
            ) {
                Log.e(
                    TAG,
                    "onFailureThrowable: ${t.message}"
                )
                close(Exception(t.message)) // Close the flow with an exception
            }
        })

        awaitClose { client.cancel() }
    }


    fun getPet(email: String) : Flow<PetResponse> = callbackFlow {

        val client = petApiService.getPetByEmail(email)
        client.enqueue(object : Callback<PetResponse> {
            override fun onResponse(
                call: Call<PetResponse>,
                response: Response<PetResponse>,
            ) {
                if (response.isSuccessful) {
                    val petResponse = response.body()!!
                    Log.d(TAG, petResponse.toString())
                    trySend(petResponse) // Emit the response value
                    close() // Close the flow after emitting the value

                } else {

                    Log.e(
                        TAG,
                        "onFailureResponse : ${response.message()}"
                    )
                    close(
                        Exception(response.message())
                    ) // Close the flow with an exception

                }
            }

            override fun onFailure(
                call: Call<PetResponse>,
                t: Throwable
            ) {
                Log.e(
                    TAG,
                    "onFailureThrowable: ${t.message}"
                )
                close(Exception(t.message)) // Close the flow with an exception
            }
        })

        awaitClose { client.cancel() }
    }

}