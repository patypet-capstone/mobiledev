package com.ahmrh.patypet.utils

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ahmrh.patypet.di.Injection
import com.ahmrh.patypet.ui.screen.auth.AuthViewModel
import com.ahmrh.patypet.ui.screen.patypet.MainViewModel
import com.ahmrh.patypet.ui.screen.patypet.pet.PetViewModel

class ViewModelFactory(private val context: Context) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            return AuthViewModel(Injection.provideAuthRepository(context)) as T
        }
        else if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(Injection.provideAuthRepository(context), Injection.providePetRepository(context)) as T
        }
        else if (modelClass.isAssignableFrom(PetViewModel::class.java)) {
            return PetViewModel(Injection.providePetRepository(context)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

}