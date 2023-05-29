package com.ahmrh.patypet.ui.util

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ahmrh.patypet.di.Injection
import com.ahmrh.patypet.ui.screen.auth.AuthViewModel

class ViewModelFactory(private val context: Context) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            return AuthViewModel(Injection.provideAuthRepository(context)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}