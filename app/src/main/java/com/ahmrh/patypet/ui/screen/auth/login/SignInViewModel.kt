package com.ahmrh.patypet.ui.screen.auth.register

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmrh.patypet.common.Resource
import com.ahmrh.patypet.common.UiState
import com.ahmrh.patypet.domain.use_case.auth.AuthUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val authUseCases: AuthUseCases
) : ViewModel() {

    private val _uiState = mutableStateOf<UiState<String>>(
        UiState.Idle)
    val uiState: State<UiState<String>> = _uiState

    fun resetState(){
        _uiState.value = UiState.Idle
    }

    fun signIn(
        email: String,
        password: String
    ) {
        Log.d("SignUpViewModel", "Sign Up Clicked" )
        authUseCases.signIn(email, password, viewModelScope)
            .onEach{ result ->
                when(result) {
                    is Resource.Success -> {
                        _uiState.value = UiState.Success("Authorized User")
                        Log.d(TAG, "Authorized User")
                    }
                    is Resource.Error -> {
                        _uiState.value = UiState.Error(result.message ?: "Unexpected Error Occurred")
                    }
                    is Resource.Loading -> {
                        _uiState.value = UiState.Loading
                    }
                }
            }.launchIn(viewModelScope)

    }

    companion object {
        const val TAG = "SignInViewModel  "
    }
}