package com.ahmrh.patypet.domain.use_case.auth

import android.util.Log
import com.ahmrh.patypet.common.Resource
import com.ahmrh.patypet.data.repositories.AuthRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    operator fun invoke(
        email: String,
        password: String,
        scope: CoroutineScope
    ): Flow<Resource<String>> = flow {
        Log.d("SignInUseCase", "Sign In Invoked")
        try {
            emit(Resource.Loading())
            repository.login(email, password, scope)
                .catch{
                    throw(it)
                }
                .collect{
                    emit(Resource.Success("Authorized User"))
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