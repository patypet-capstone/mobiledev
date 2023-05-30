package com.ahmrh.patypet.ui.screen.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmrh.patypet.data.model.UserLogin
import com.ahmrh.patypet.data.repositories.AuthRepository
import com.ahmrh.patypet.utils.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AuthViewModel(private val repository: AuthRepository): ViewModel() {
    private val _uiState: MutableStateFlow<UiState<UserLogin>> = MutableStateFlow(
        UiState.Loading)
    val uiState: StateFlow<UiState<UserLogin>>
        get() = _uiState

    fun login(email: String, password: String) {
        _uiState.value = UiState.Loading

        val response = repository.login(email, password, viewModelScope)

//        response.observeForever(object : Observer<RemoteResponse> {
//            override fun onChanged(value: RemoteResponse) {
//                value.let {
//                    _uiState.value = UiState.Success(response)
//                    response.removeObserver(this)
//                }
//            }
//        })

    }

    companion object{
        const val TAG = "AuthViewModel"
    }
}