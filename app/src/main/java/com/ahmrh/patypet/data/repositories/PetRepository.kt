package com.ahmrh.patypet.data.repositories

import android.util.Log
import com.ahmrh.patypet.data.remote.responses.ArticleResponseItem
import com.ahmrh.patypet.data.remote.responses.ChangeNameResponse
import com.ahmrh.patypet.data.remote.responses.PetByIdResponse
import com.ahmrh.patypet.data.remote.responses.PetResponse
import com.ahmrh.patypet.data.remote.responses.PredictionResponse
import com.ahmrh.patypet.data.remote.responses.SaveResponse
import com.ahmrh.patypet.data.remote.retrofit.AuthApiService
import com.ahmrh.patypet.data.remote.retrofit.PetApiService
import com.ahmrh.patypet.domain.utils.reduceFileImage
import com.google.gson.JsonObject
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

    fun fetchArticle(jenis: String?) : Flow<List<ArticleResponseItem>> = callbackFlow {

        val client = authApiService.fetchArticle(jenis ?: "")
        client.enqueue(object : Callback<List<ArticleResponseItem>> {
            override fun onResponse(
                call: Call<List<ArticleResponseItem>>,
                response: Response<List<ArticleResponseItem>>,
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
                call: Call<List<ArticleResponseItem>>,
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

    fun getPetById(email: String, id: Int) : Flow<PetByIdResponse> = callbackFlow {

        val client = petApiService.getPetByEmailAndId(email, id)
        client.enqueue(object : Callback<PetByIdResponse> {
            override fun onResponse(
                call: Call<PetByIdResponse>,
                response: Response<PetByIdResponse>,
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
                call: Call<PetByIdResponse>,
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

    fun savePet() : Flow<SaveResponse> = callbackFlow {

        val client = petApiService.savePet()
        client.enqueue(object : Callback<SaveResponse> {
            override fun onResponse(
                call: Call<SaveResponse>,
                response: Response<SaveResponse>,
            ) {
                if (response.isSuccessful) {
                    val saveResponse = response.body()!!
                    Log.d(TAG, saveResponse.toString())
                    trySend(saveResponse) // Emit the response value
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
                call: Call<SaveResponse>,
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


    fun changeName(id: Int, name: String) : Flow<ChangeNameResponse> = callbackFlow {

        val rawJsonObject = JsonObject()
        rawJsonObject.addProperty("prediction_id", id)
        rawJsonObject.addProperty("name", name)

        Log.d(TAG, rawJsonObject.toString())
        val client = petApiService.changeName(id, name)
        client.enqueue(object : Callback<ChangeNameResponse> {
            override fun onResponse(
                call: Call<ChangeNameResponse>,
                response: Response<ChangeNameResponse>,
            ) {
                if (response.isSuccessful) {
                    val changeNameResponse = response.body()!!
                    Log.d(TAG, changeNameResponse.toString())
                    trySend(changeNameResponse) // Emit the response value
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
                call: Call<ChangeNameResponse>,
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