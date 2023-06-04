package com.ahmrh.patypet.ui.screen.patypet.pet

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmrh.patypet.common.Resource
import com.ahmrh.patypet.data.remote.responses.PredictionResponse
import com.ahmrh.patypet.domain.state.UiState
import com.ahmrh.patypet.domain.use_case.pet.PetUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class PetViewModel @Inject constructor(
    private val petUseCases: PetUseCases
): ViewModel() {

    private val _uiState = mutableStateOf<UiState<PredictionResponse>>(UiState.Idle)
    val uiState: State<UiState<PredictionResponse>> = _uiState

    private val _visiblePermissionDialogQueue = mutableStateListOf<String>()
    val visiblePermissionDialogQueue
        get() = _visiblePermissionDialogQueue

    private val _permission = mutableListOf<String>()
    val permission
        get() = _permission

    fun dismissDialog(){
        visiblePermissionDialogQueue.removeLast()
    }

    fun onPermissionResult(
        permission: String,
        isGranted: Boolean
    ){
        if(!isGranted){
            visiblePermissionDialogQueue.add(0, permission)
        } else {
            _permission.add(0, permission)
        }
    }

    fun predict(
        img : Any
    ) {
        Log.d("SignUpViewModel", "Sign Up Clicked" )
        petUseCases.predict(img)
            .onEach{ result ->
                when(result) {
                    is Resource.Success -> {
                        _uiState.value = UiState.Success(result.data!!)
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