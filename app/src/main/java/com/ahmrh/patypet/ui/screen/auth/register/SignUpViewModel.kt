package com.ahmrh.patypet.ui.screen.auth.register

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.ahmrh.patypet.common.Resource
import com.ahmrh.patypet.domain.state.UiState
import com.ahmrh.patypet.domain.use_case.auth.AuthUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authUseCases: AuthUseCases
) : ViewModel() {

    private val _state = mutableStateOf<UiState<String>>(UiState.Idle)
    val state: State<UiState<String>> = _state

    fun signUp(
        name: String,
        email: String,
        password: String
    ) {
        authUseCases.signUp(name, email, password).onEach { result ->
            when(result) {
                is Resource.Success -> {
                    _state.value = UiState.Success("User Registered")
                }
                is Resource.Error -> {
                    _state.value = UiState.Error(result.message ?: "Unexpected Error Occured")
                }
                is Resource.Loading -> {
                    _state.value = UiState.Loading
                }
            }
        }

    }
}