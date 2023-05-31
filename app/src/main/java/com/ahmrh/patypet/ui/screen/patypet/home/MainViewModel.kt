package com.ahmrh.patypet.ui.screen.patypet.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmrh.patypet.data.remote.responses.RemoteResponse
import com.ahmrh.patypet.data.repositories.AuthRepository
import com.ahmrh.patypet.data.repositories.PetRepository
import com.ahmrh.patypet.utils.AuthState
import com.ahmrh.patypet.utils.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val authRepository: AuthRepository,
    private val petRepository: PetRepository
): ViewModel() {

    private val _uiState: MutableStateFlow<UiState<RemoteResponse>> =
        MutableStateFlow(
            UiState.Idle
        )
    val uiState: StateFlow<UiState<RemoteResponse>>
        get() = _uiState

    private val _authState: MutableStateFlow<AuthState> =
        MutableStateFlow(getAuthState())
    val authState: StateFlow<AuthState>
        get() = _authState


    private fun getAuthState(): AuthState {
        val isLogin = mutableStateOf(false)
        viewModelScope.launch {
            authRepository.isLogin()
                .collect {
                    isLogin.value = it
                }
        }

        return if (isLogin.value) AuthState.Unknown else AuthState.Authenticated(
            authRepository.getToken()!!
        )
    }
}