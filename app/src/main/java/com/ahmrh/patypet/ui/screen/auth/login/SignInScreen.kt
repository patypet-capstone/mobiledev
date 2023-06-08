package com.ahmrh.patypet.ui.screen.auth.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ahmrh.patypet.ui.components.loading.Loading
import com.ahmrh.patypet.ui.components.button.LongButton
import com.ahmrh.patypet.ui.components.StaticHeader
import com.ahmrh.patypet.ui.theme.PatypetTheme
import com.ahmrh.patypet.domain.state.UiState
import com.ahmrh.patypet.domain.utils.isValidEmail
import com.ahmrh.patypet.domain.utils.isValidPassword
import com.ahmrh.patypet.ui.components.CustomInputField
import com.ahmrh.patypet.ui.components.dialog.CustomDialog

@Composable
fun SignInScreen(
    uiState: State<UiState<String>>,
    onSignIn: (email: String, password: String) -> Unit,
    authenticate: () -> Unit,
    navigateToSignIn: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),

        ) {
        Button(
            onClick = authenticate
        ){
            Text("Debug")
        }
        StaticHeader(
            modifier = Modifier,
            type = "Dog"
        )
        SignInForm(
            onSignIn
        )
    }

    when(uiState.value){
        is UiState.Idle -> {
            // Nothing happened
        }
        is UiState.Loading -> {
            Loading()
        }
        is UiState.Success -> {
            LaunchedEffect(key1 = true){
                authenticate()
            }
        }

        is UiState.Error -> {
            val message = (uiState.value as UiState.Error).errorMessage
            CustomDialog(title = "Error Occurred", body = message)

        }
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

            var emailError by remember { mutableStateOf(false)}
            var passwordError by remember { mutableStateOf(false)}

            CustomInputField(
                label = "Email",
                inputText = email,
                isError = emailError,
                modifier = Modifier.width(312.dp),
                onTextChange = {
                    email = it
                    emailError = !isValidEmail(email)
                }
            )
            Column(
                horizontalAlignment = Alignment.End,
            ) {

                CustomInputField(
                    label = "Password",
                    inputText = password,
                    isPassword = true,
                    isError = passwordError,
                    onTextChange = {
                        password = it
                        passwordError = !isValidPassword(password)
                    },
                    modifier = Modifier.width(312.dp)
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
                isError = passwordError || emailError,
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

    }
}