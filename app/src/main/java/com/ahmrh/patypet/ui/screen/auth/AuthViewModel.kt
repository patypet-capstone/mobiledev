package com.ahmrh.patypet.ui.screen.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmrh.patypet.data.model.RemoteResponse
import com.ahmrh.patypet.data.remote.responses.LoginResponse
import com.ahmrh.patypet.data.repositories.AuthRepository
import com.ahmrh.patypet.ui.util.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(private val repository: AuthRepository): ViewModel() {
    private val _uiState: MutableStateFlow<UiState<RemoteResponse>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<RemoteResponse>>
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