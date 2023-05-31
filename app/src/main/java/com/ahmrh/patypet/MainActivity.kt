package com.ahmrh.patypet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ahmrh.patypet.ui.screen.auth.AuthViewModel
import com.ahmrh.patypet.ui.screen.patypet.home.MainViewModel
import com.ahmrh.patypet.ui.screen.patypet.pet.PetViewModel
import com.ahmrh.patypet.ui.theme.PatypetTheme
import com.ahmrh.patypet.utils.AuthState
import com.ahmrh.patypet.utils.ViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            PatypetTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    val authViewModel: AuthViewModel = viewModel(
                        factory = ViewModelFactory(LocalContext.current)
                    )


                    val authState = authViewModel.authState

                    authState.collectAsState(initial = AuthState.Unknown).value.let { state ->
                        when (state) {
                            is AuthState.Unknown -> {
                                Authenticator()
                            }

                            is AuthState.Authenticated -> {
                                PatypetApp()
                            }

                        }
                    }
                }

            }
        }
    }
}
