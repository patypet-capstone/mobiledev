package com.ahmrh.patypet.domain.use_case.pet

import android.net.Uri
import android.util.Log
import com.ahmrh.patypet.common.Resource
import com.ahmrh.patypet.data.remote.responses.PredictionResponse
import com.ahmrh.patypet.data.repositories.PetRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.File
import java.io.IOException
import javax.inject.Inject

class PredictUseCase @Inject constructor(
    private val repository: PetRepository
) {

    operator fun invoke(
        img: Any
    ): Flow<Resource<PredictionResponse>> = flow {
        Log.d("PredictUseCase", "PredictInvoked")

        try {
            emit(Resource.Loading())
            var imgFile: File? = null

            if(img is Uri){
                imgFile = File(img.path!!)
            } else if(img is File){
                imgFile = img
            }

            imgFile?.let {
                repository.predict(it)
                    .collect{ prediction ->
                        emit(Resource.Success(prediction))
                    }
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
        }
    }


}