package com.ahmrh.patypet.ui.screen.auth.login

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ahmrh.patypet.data.remote.responses.RemoteResponse
import com.ahmrh.patypet.ui.components.LoadingBar
import com.ahmrh.patypet.ui.components.LongButton
import com.ahmrh.patypet.ui.components.LongInputField
import com.ahmrh.patypet.ui.components.StaticHeader
import com.ahmrh.patypet.ui.theme.PatypetTheme
import com.ahmrh.patypet.domain.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun SignInScreen(
    uiState: StateFlow<UiState<RemoteResponse>>,
    onSignIn: (
        email: String,
        password: String
    ) -> Unit,
    forceLogin: () -> Unit,
) {

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),

        ) {
        StaticHeader(
            modifier = Modifier,
            type = "Dog"
        )
        SignInForm(
            onSignIn
        )
    }

    uiState.collectAsState(initial = UiState.Idle).value.let { state ->
        when (state) {
            is UiState.Idle -> {
            }

            is UiState.Loading -> {
                LoadingBar()
            }

            is UiState.Success -> {
                val test = state.data

                AuthDialog(
                    success = test.success,
                    message = test.message,
                    forceLogin = forceLogin
                )

            }

            is UiState.Error -> {
                Text("Error")

            }
        }
    }
}

@Composable
fun AuthDialog(
    message: String,
    success: Boolean,
    forceLogin: () -> Unit
) {
    var isOpenState by remember { mutableStateOf(true) }

    if (isOpenState) {
        AlertDialog(
            onDismissRequest = {
                isOpenState = false
            },
            title = {
                Text(
                    text = if(!success) "Server is Down" else "Authorized User"
                )
            },
            text = {
                Text(
                    text = if (success) "Your login detail is authorized"
                    else "it seems the server is down, are you the developer team?"
                )
            },
            dismissButton = {

                TextButton(
                    onClick = {
                        isOpenState = false
                    }
                ) {
                    Text("No")
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        isOpenState = false
                        forceLogin()
                    }
                ) {
                    Text("Yes")
                }
            },


            )
    }
}


@Composable
fun SignInForm(
    onSignIn: (email: String, password: String) -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(vertical = 24.dp)
    ) {

        Text(
            "Welcome Back",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold
        )
        Text(
            "Sign In with your Account",
            style = MaterialTheme.typography.bodyMedium,

            )
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .padding(vertical = 48.dp)
        ) {

            var email by remember { mutableStateOf("") }
            var password by remember { mutableStateOf("") }
            LongInputField(
                label = "Email",
                inputText = email,
                onTextChange = { email = it }
            )
            Column(
                horizontalAlignment = Alignment.End,
            ) {

                LongInputField(
                    label = "Password",
                    inputText = password,
                    isPassword = true,
                    onTextChange = { password = it }
                )
                TextButton(
                    onClick = {},
                    modifier = Modifier
                        .height(36.dp)
                ) {
                    Text(
                        "Forgot password?",
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
            LongButton(
                text = "Sign In",
                modifier = Modifier.padding(vertical = 8.dp),
                color = MaterialTheme.colorScheme.secondary,
                textColor = MaterialTheme.colorScheme.onSecondary,
                onClick = {
                    onSignIn(email, password)
                }
            )
        }

    }

}

@Preview(showBackground = true)
@Composable
fun SignInScreenPreview() {
    PatypetTheme {
        SignInScreen(
            uiState = MutableStateFlow(
                UiState.Success(
                    RemoteResponse(
                        success = false,
                        message = ""
                    )
                )
            ),
            onSignIn = { email, password ->
                Log.d(
                    "Testing Input",
                    "$email $password"
                )

            },
            {}
        )
    }
}