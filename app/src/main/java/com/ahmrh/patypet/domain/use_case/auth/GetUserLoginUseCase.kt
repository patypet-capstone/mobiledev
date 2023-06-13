package com.ahmrh.patypet.domain.use_case.auth

import android.util.Log
import com.ahmrh.patypet.common.Resource
import com.ahmrh.patypet.data.local.AppPreferences
import com.ahmrh.patypet.data.repositories.AuthRepository
import com.ahmrh.patypet.domain.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetUserLoginUseCase @Inject constructor(
    private val preferences: AppPreferences,
    private val repository: AuthRepository
) {
    operator fun invoke(): Flow<Resource<User>> = flow {
        Log.d("SignInUseCase", "Sign In Invoked")
        val token = preferences.getToken()
        if (token == "") {
            emit(Resource.Error(message = "unauthorized"))
            Log.d("GetUserLoginUseCase", "it's unauthorized")
            return@flow
        } else
            try {
                emit(Resource.Loading())
                repository.getUser(token!!)
                    .catch {
                        throw (it)
                    }
                    .collect {
                        emit(
                            Resource.Success(
                                User(
                                    id = it.id,
                                    name = it.name,
                                    email = it.email,
                                    token = token
                                )
                            )
                        )
                        Log.d("GetUserLoginUseCase", "it's authorized")
                    }

            } catch (e: HttpException) {
                emit(
                    Resource.Error(
                        message = e.localizedMessage
                            ?: "An unexpected error occurred"
                    )
                )
                Log.e("GetUserLoginUseCase", e.message())
            } catch (e: IOException) {
                Log.e("GetUserLoginUseCase", e.message ?: "aosdoasjda")
                emit(Resource.Error(message = "Couldn't reach server"))
            } catch (e: Exception) {
                Log.e("GetUserLoginUseCase", e.message ?: "aosdoasjda")
                emit(
                    Resource.Error(
                        message = e.message
                            ?: "An unexpected error occurred"
                    )
                )
            }
    }

}