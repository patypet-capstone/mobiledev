package com.ahmrh.patypet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.ahmrh.patypet.data.model.UserLogin
import com.ahmrh.patypet.ui.theme.PatypetTheme
import com.ahmrh.patypet.utils.AuthState

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PatypetTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val authState: AuthState<UserLogin> = AuthState.Unknown

                    when(authState){
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
