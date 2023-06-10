package com.ahmrh.patypet.ui.screen.auth

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmrh.patypet.data.remote.responses.RemoteResponse
import com.ahmrh.patypet.data.repositories.AuthRepository
import com.ahmrh.patypet.domain.state.AuthState
import com.ahmrh.patypet.domain.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {
//    private val _authState: MutableStateFlow<AuthState> =
//        MutableStateFlow(AuthState.Unknown)
//    val authState: StateFlow<AuthState>
//        get() = _authState
//

    private val _authState = mutableStateOf<AuthState>(AuthState.Unknown)
    val authState: State<AuthState> = _authState
    fun getAuthState() {
        viewModelScope.launch {
            repository.isLogin()
                .collect {
                    Log.d(TAG, it.toString())
                    if (it) _authState.value =
                        AuthState.Authenticated(
                            repository.getToken() ?: ""
                        )
                    else _authState.value =
                        AuthState.Unknown
                }
            Log.d("Testing Auth", repository.getToken().toString())
        }
    }
//
    fun logout() {
        viewModelScope.launch {
            Log.d(TAG, "logout")
            repository.endSession()
        }
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

    fun forceLogin() {
        repository.forceLogin(viewModelScope)
    }

    companion object {
        const val TAG = "AuthViewModel"
    }
}