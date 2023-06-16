package com.ahmrh.patypet.ui.screen.patypet.home

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmrh.patypet.common.AuthState
import com.ahmrh.patypet.common.Resource
import com.ahmrh.patypet.data.remote.responses.ArticleResponseItem
import com.ahmrh.patypet.data.remote.responses.PetResponse
import com.ahmrh.patypet.common.UiState
import com.ahmrh.patypet.di.Injection.init
import com.ahmrh.patypet.domain.model.User
import com.ahmrh.patypet.domain.use_case.auth.AuthUseCases
import com.ahmrh.patypet.domain.use_case.pet.PetUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel@Inject constructor(
    private val petUseCases: PetUseCases,
    private val authUseCases: AuthUseCases
): ViewModel()  {


    private val _articleUiState = mutableStateOf<UiState<List<ArticleResponseItem>>>(
        UiState.Idle)
    val articleUiState: State<UiState<List<ArticleResponseItem>>> = _articleUiState

    private val _petUiState = mutableStateOf<UiState<PetResponse>>(
        UiState.Idle)
    val petUiState: State<UiState<PetResponse>> = _petUiState


    init{
        fetchData()
    }

    fun fetchData(){
        viewModelScope.launch{

            _petUiState.value = UiState.Loading
            _articleUiState.value = UiState.Loading

            fetchArticle()
            fetchPet()
        }
    }
    fun fetchArticle(jenis: String? = null) {
        Log.d("SignUpViewModel", "Sign Up Clicked" )
        petUseCases.fetchArticle(jenis)
            .onEach{ result ->
                when(result) {
                    is Resource.Success -> {
                        _articleUiState.value = UiState.Success(result.data!!)
                    }
                    is Resource.Error -> {
                        _articleUiState.value = UiState.Error(result.message ?: "Unexpected Error Occured")
                        fetchArticle()
                    }
                    is Resource.Loading -> {
                        _articleUiState.value = UiState.Loading
                    }
                }
            }.launchIn(viewModelScope)

    }
    fun getPet(){
        viewModelScope.launch {
            fetchPet()
        }

    }

    suspend fun fetchPet(){

        authUseCases.getUserLogin()
            .collect { user ->
                if(user is Resource.Success){
                    val email = user.data?.email!!

                    petUseCases.getPet(email)
                        .onEach{ result ->
                            when(result) {
                                is Resource.Success -> {
                                    _petUiState.value = UiState.Success(result.data!!)
                                    Log.d("HomeViewModel", result.data.toString())
                                }
                                is Resource.Error -> {
                                    _petUiState.value = UiState.Error(result.message ?: "Unexpected Error Occured")
                                }
                                is Resource.Loading -> {
                                    _petUiState.value = UiState.Loading
                                }
                            }
                        }.launchIn(viewModelScope)
                }

            }

    }


}