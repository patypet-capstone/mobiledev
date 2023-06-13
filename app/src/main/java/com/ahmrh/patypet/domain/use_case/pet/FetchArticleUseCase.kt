package com.ahmrh.patypet.domain.use_case.pet

import android.net.Uri
import android.util.Log
import com.ahmrh.patypet.common.Resource
import com.ahmrh.patypet.data.remote.responses.ArticleResponse
import com.ahmrh.patypet.data.remote.responses.ArticleResponseItem
import com.ahmrh.patypet.data.remote.responses.PredictionResponse
import com.ahmrh.patypet.data.repositories.PetRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.File
import java.io.IOException
import javax.inject.Inject

class FetchArticleUseCase @Inject constructor(
    private val repository: PetRepository
) {

    operator fun invoke(
        jenis: String?
    ): Flow<Resource<List<ArticleResponseItem>>> = flow {
        Log.d("PredictUseCase", "PredictInvoked")

        try {
            repository.fetchArticle(jenis ?: "")
                .catch {
                    throw(it)
                }
                .collect{
                    emit(Resource.Success(it))
                }


        } catch (e: HttpException) {
            Log.e("UseCase", e.message())
            emit(
                Resource.Error(
                    message = e.localizedMessage
                        ?: "Couldn't reach server"
                )
            )
        } catch (e: IOException) {
            emit(Resource.Error(message = "Couldn't reach server"))
        } catch (e: Exception) {
            Log.e("UseCase", e.message.toString())
            emit(
                Resource.Error(
                    message = e.message
                        ?: "An unexpected error occurred"
                )
            )
        }
    }

}