package com.ahmrh.patypet.ui.screen.auth

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmrh.patypet.common.AuthState
import com.ahmrh.patypet.common.Resource
import com.ahmrh.patypet.data.repositories.AuthRepository
import com.ahmrh.patypet.di.Injection.init
import com.ahmrh.patypet.domain.model.User
import com.ahmrh.patypet.domain.use_case.auth.AuthUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository,
    private val authUseCases: AuthUseCases
) : ViewModel() {

    private val _authState = mutableStateOf<AuthState>(
        AuthState.Unknown
    )
    val authState: State<AuthState> = _authState

    private val _user = mutableStateOf(User())
    val user = _user

    init{
        getUserLogin()
        Log.d("AuthViewModel", "View Model initiated")
    }

    private fun getUserLogin() {
        Log.d("SignUpViewModel", "Sign Up Clicked")
        authUseCases.getUserLogin()
            .onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        _authState.value =
                            AuthState.Authenticated(result.data!!)
                        _user.value = result.data
                    }

                    is Resource.Error -> {
                        if (result.message == "unauthorized") {
                            _authState.value =
                                AuthState.Unknown
                        }
                    }

                    is Resource.Loading -> {

                    }
                }
            }.launchIn(viewModelScope)
    }

    fun getAuthState() {
        viewModelScope.launch {
            repository.isLogin()
                .collect {
                    Log.d(TAG, it.toString())
                    if (it) _authState.value =
                        AuthState.Authenticated()
                    else _authState.value =
                        AuthState.Unknown
                }
            Log.d(
                "Testing Auth",
                repository.getToken().toString()
            )
        }
    }

    //
    suspend fun logout() {
        Log.d(TAG, "logout")
        repository.endSession()
        getAuthState()
    }
//
//
//    fun login(email: String, password: String) {
//
//        viewModelScope.launch {
//            _uiState.value = UiState.Loading
//            repository.login(
//                email,
//                password,
//                viewModelScope
//            )
//                .catch {
//                    _uiState.value =
//                        UiState.Error(it.message.toString())
//                }
//                .collect { response ->
//                    _uiState.value =
//                        UiState.Success(response)
//                    getAuthState()
//                }
//
//
//            _uiState.value = UiState.Idle
//        }
//    }

//    fun register(
//        name: String,
//        email: String,
//        password: String
//    ) {
//
//        _uiState.value = UiState.Loading
//        viewModelScope.launch {
//
//            repository.register(name, email, password)
//                .catch {
//                    _uiState.value =
//                        UiState.Error(it.message.toString())
//                }
//                .collect { response ->
//                    _uiState.value =
//                        UiState.Success(data = response)
//                }
//
//            _uiState.value = UiState.Idle
//        }
//
//    }


    companion object {
        const val TAG = "AuthViewModel"
    }
}