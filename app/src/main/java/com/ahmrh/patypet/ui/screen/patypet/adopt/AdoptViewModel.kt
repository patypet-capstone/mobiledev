package com.ahmrh.patypet.ui.screen.patypet.adopt

import androidx.lifecycle.ViewModel
import com.ahmrh.patypet.domain.use_case.pet.PetUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AdoptViewModel @Inject constructor(
    private val petUseCases: PetUseCases,
) : ViewModel(){

}