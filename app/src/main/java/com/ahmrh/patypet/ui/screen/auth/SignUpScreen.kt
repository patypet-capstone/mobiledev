package com.ahmrh.patypet.ui.screen.auth

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import com.ahmrh.patypet.data.model.User
import com.ahmrh.patypet.ui.components.LoadingBar
import com.ahmrh.patypet.ui.components.LongButton
import com.ahmrh.patypet.ui.components.LongInputField
import com.ahmrh.patypet.ui.components.StaticHeader
import com.ahmrh.patypet.ui.theme.PatypetTheme
import com.ahmrh.patypet.utils.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun SignUpScreen(
    uiState: StateFlow<UiState<RemoteResponse>>,
    onSignUp : (
        name: String,
        email: String,
        password: String
    ) -> Unit,
) {
    val openDialog = remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {
        StaticHeader(type = "Cat")
        SignUpForm(
            onSignUp
        )
    }

    uiState.collectAsState(initial = UiState.Idle).value.let { state ->
        when(state){
            is UiState.Idle -> {
            }
            is UiState.Loading -> {
                LoadingBar()
            }
            is UiState.Success -> {
                val test = uiState.value

                Text(" haha ${test.toString()}")
            }
            is UiState.Error -> {
                Text("Error")

            }
        }
    }

}


@Composable
fun SignUpForm(
    onSignUp: (name: String, email: String, password: String) -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(vertical = 24.dp)
    ) {
        Text(
            "Register",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold
        )
        Text(
            "Sign Up with your Account",
            style = MaterialTheme.typography.bodyMedium,

            )
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .padding(vertical = 24.dp)
        ) {

            var name by remember { mutableStateOf("") }
            var email by remember { mutableStateOf("") }
            var password by remember { mutableStateOf("") }

            LongInputField(
                label = "Name",
                inputText = name,
                onTextChange = { name = it }
            )
            LongInputField(
                label = "Email",
                inputText = email,
                onTextChange = { email = it }
            )

            LongInputField(
                label = "Password",
                inputText = password,
                isPassword = true,
                onTextChange = { password = it }
            )
            LongButton(
                text = "Sign Up",
                modifier = Modifier.padding(vertical = 16.dp),
                color = MaterialTheme.colorScheme.secondary,
                textColor = MaterialTheme.colorScheme.onSecondary,
                onClick = {
                    onSignUp(name, email, password)
                }
            )
        }

    }

}

data class SignUpState(
    val name: String,
    val email: String,
    val password: String,
)

@Preview(showBackground = true)
@Composable
fun SignUpScreenPreview() {
    PatypetTheme {
        SignUpScreen(
            uiState = MutableStateFlow(
                UiState.Success(
                    RemoteResponse(
                        success = false,
                        message = ""
                    )
                )
            ), onSignUp = { name, email, password ->
                Log.d(
                    "Testing Input",
                    "$name $email $password"
                )
            }
        )
    }
}