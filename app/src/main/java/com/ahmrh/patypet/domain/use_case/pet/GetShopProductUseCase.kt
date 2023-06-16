package com.ahmrh.patypet.domain.use_case.pet

import android.util.Log
import com.ahmrh.patypet.common.Resource
import com.ahmrh.patypet.data.remote.responses.PetResponse
import com.ahmrh.patypet.data.remote.responses.ShopResponseItem
import com.ahmrh.patypet.data.repositories.PetRepository
import com.ahmrh.patypet.data.repositories.ShopRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetShopProductUseCase @Inject constructor(
    private val repository: ShopRepository
) {
    operator fun invoke(
        product: String?,
        jenis: String?
    ): Flow<Resource<List<ShopResponseItem>>> = flow {
        Log.d("PredictUseCase", "PredictInvoked")

        try {
            repository.getShopProduct(product, jenis)
                .catch{
                    throw(it)
                }
                .collect {
                    emit(Resource.Success(it))
                }

        } catch (e: HttpException) {
            emit(
                Resource.Error(
                    message = e.localizedMessage
                        ?: "An unexpected error occurred"
                )
            )
        } catch (e: IOException) {
            emit(Resource.Error(message = "Couldn't reach server"))
        } catch (e: Exception) {
            emit(
                Resource.Error(
                    message = e.message
                        ?: "An unexpected error occurred"
                )
            )
        }
    }
}