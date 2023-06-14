package com.ahmrh.patypet.data.repositories

import android.util.Log
import com.ahmrh.patypet.data.remote.responses.ShopResponseItem
import com.ahmrh.patypet.data.remote.retrofit.AuthApiService
import com.ahmrh.patypet.data.remote.retrofit.PetApiService
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ShopRepository(
    private val apiService: PetApiService,
)  {

    fun getShopProduct(product: String?, jenis: String?) : Flow<List<ShopResponseItem>> = callbackFlow {

        val client = apiService.getShopProduct(product ?: "all", jenis ?: "")
        client.enqueue(object : Callback<List<ShopResponseItem>> {
            override fun onResponse(
                call: Call<List<ShopResponseItem>>,
                response: Response<List<ShopResponseItem>>,
            ) {
                if (response.isSuccessful) {
                    val productResponse = response.body()!!
                    Log.d(PetRepository.TAG, productResponse.toString())
                    trySend(productResponse) // Emit the response value
                    close() // Close the flow after emitting the value

                } else {

                    Log.e(
                        PetRepository.TAG,
                        "onFailureResponse : ${response.message()}"
                    )
                    close(
                        Exception(response.message())
                    ) // Close the flow with an exception

                }
            }

            override fun onFailure(
                call: Call<List<ShopResponseItem>>,
                t: Throwable
            ) {
                Log.e(
                    PetRepository.TAG,
                    "onFailureThrowable: ${t.message}"
                )
                close(Exception(t.message)) // Close the flow with an exception
            }
        })

        awaitClose { client.cancel() }
    }


}