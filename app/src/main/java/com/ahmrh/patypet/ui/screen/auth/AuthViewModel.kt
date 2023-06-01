package com.ahmrh.patypet.ui.screen.auth

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmrh.patypet.data.remote.responses.RemoteResponse
import com.ahmrh.patypet.data.repositories.AuthRepository
import com.ahmrh.patypet.utils.AuthState
import com.ahmrh.patypet.utils.UiState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

class AuthViewModel(private val repository: AuthRepository) :
    ViewModel() {
    private val _uiState: MutableStateFlow<UiState<RemoteResponse>> =
        MutableStateFlow(
            UiState.Idle
        )
    val uiState: StateFlow<UiState<RemoteResponse>>
        get() = _uiState

    private val _authState: MutableStateFlow<AuthState> = MutableStateFlow(AuthState.Unknown)
    val authState: StateFlow<AuthState>
        get() = _authState

    fun getAuthState() {
        viewModelScope.launch {
            repository.isLogin()
                .collect {
                    Log.d(TAG, it.toString())
                    if(it) _authState.value = AuthState.Authenticated(repository.getToken()!!)
                    else _authState.value = AuthState.Unknown
                }

        }

    }

    fun logout(){
        viewModelScope.launch{
            Log.d(TAG, "logout")
            repository.endSession()
        }
    }


    fun login(email: String, password: String) {

        viewModelScope.launch {
            _uiState.value = UiState.Loading
            repository.login(email, password, viewModelScope)
                .catch {
                    _uiState.value =
                        UiState.Error(it.message.toString())
                }
                .collect { response ->
                    _uiState.value =
                        UiState.Success(response)
                    getAuthState()
                }

            _uiState.value = UiState.Idle
        }
    }

    fun register(
        name: String,
        email: String,
        password: String
    ) {

        viewModelScope.launch {
            _uiState.value = UiState.Loading

            repository.register(name, email, password)
                .catch {
                    _uiState.value =
                        UiState.Error(it.message.toString())
                }
                .collect { response ->
                    _uiState.value =
                        UiState.Success(response)
                }

            _uiState.value = UiState.Idle
        }

    }

    companion object {
        const val TAG = "AuthViewModel"
    }
}