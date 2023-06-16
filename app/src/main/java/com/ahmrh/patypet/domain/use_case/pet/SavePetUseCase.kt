package com.ahmrh.patypet.domain.use_case.pet

import android.util.Log
import com.ahmrh.patypet.common.Resource
import com.ahmrh.patypet.data.remote.responses.PetByIdResponse
import com.ahmrh.patypet.data.remote.responses.SaveResponse
import com.ahmrh.patypet.data.repositories.PetRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class SavePetUseCase @Inject constructor(
    private val repository: PetRepository
) {
    operator fun invoke(
        name: String
    ): Flow<Resource<SaveResponse>> = flow {
        Log.d("PredictUseCase", "PredictInvoked")

        try {
            repository.savePet()
                .catch {
                    Log.e("SavePetUseCase", it.message ?: "unexpected error at savepet repo")
                    throw (it)
                }
                .collect { saveResult ->
                    val id = saveResult.predictionId!!

                    repository.changeName(id, name)
                        .catch{

                            Log.e("SavePetUseCase", it.message ?: "unexpected error at changename repo")
                            throw(it)
                        }
                        .collect{
                            Log.d("SavePetUseCase", "changing pet name ")
                            emit(Resource.Success(saveResult))
                        }


                    Log.d("SavePetUseCase", "saving pet name ")
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