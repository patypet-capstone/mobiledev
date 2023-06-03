package com.ahmrh.patypet.ui.screen.auth.register

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmrh.patypet.common.Resource
import com.ahmrh.patypet.domain.state.UiState
import com.ahmrh.patypet.domain.use_case.auth.AuthUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authUseCases: AuthUseCases
) : ViewModel() {

    private val _uiState = mutableStateOf<UiState<String>>(UiState.Loading)
    val uiState: State<UiState<String>> = _uiState

     fun signUp(
        name: String,
        email: String,
        password: String
    ) {
        Log.d("SignUpViewModel", "Sign Up Clicked" )
        authUseCases.signUp(name, email, password)
            .onEach{ result ->
                when(result) {
                    is Resource.Success -> {
                        _uiState.value = UiState.Success("User Registered")
                    }
                    is Resource.Error -> {
                        _uiState.value = UiState.Error(result.message ?: "Unexpected Error Occured")
                    }
                    is Resource.Loading -> {
                        _uiState.value = UiState.Loading
                    }
                }
            }.launchIn(viewModelScope)

    }
}