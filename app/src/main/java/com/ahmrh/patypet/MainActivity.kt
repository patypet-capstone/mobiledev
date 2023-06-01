package com.ahmrh.patypet

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ahmrh.patypet.ui.screen.auth.AuthViewModel
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
                    authViewModel.getAuthState()
                    val authState = authViewModel.authState

//                    val authState: MutableStateFlow<AuthState> = MutableStateFlow(AuthState.Authenticated(""))

                    authState.collectAsState(initial = AuthState.Unknown).value.let { state ->
                        when (state) {
                            is AuthState.Unknown -> {
                                Authenticator()
                            }

                            is AuthState.Authenticated -> {
                                PatypetApp(
                                    onOpenAppSettings = ::openAppSettings
                                )
                            }

                        }
                    }
                }

            }
        }
    }


}

fun Activity.openAppSettings(){
    Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("com.ahmrh.patypet", packageName, null)
    ).also(::startActivity)
}

