package com.ahmrh.patypet.ui.screen.patypet.caretake

import androidx.lifecycle.ViewModel
import com.ahmrh.patypet.domain.use_case.pet.PetUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CaretakeViewModel @Inject constructor(
    private val petUseCases: PetUseCases,
) : ViewModel(){
}