package com.ahmrh.patypet.ui.screen.auth.register

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.ahmrh.patypet.ui.components.LoadingBar
import com.ahmrh.patypet.ui.components.button.LongButton
import com.ahmrh.patypet.ui.components.LongInputField
import com.ahmrh.patypet.ui.components.StaticHeader
import com.ahmrh.patypet.ui.theme.PatypetTheme
import com.ahmrh.patypet.domain.state.UiState
import com.ahmrh.patypet.domain.utils.isValidEmail
import com.ahmrh.patypet.domain.utils.isValidPassword
import com.ahmrh.patypet.ui.components.CustomInputField
import com.ahmrh.patypet.ui.components.dialog.AuthDialog

@Composable
fun SignUpScreen(
    uiState: State<UiState<String>>,
    onSignUp : (
        name: String,
        email: String,
        password: String
    ) -> Unit,
) {

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
    when(uiState.value){
        is UiState.Idle -> {
            // Nothing happened
        }
        is UiState.Loading -> {
            LoadingBar()
            Text("Loading")
            AuthDialog(title = "User Registered", body = "aye")
        }
        is UiState.Success -> {
            val message = (uiState.value as UiState.Success<String>).data
            AuthDialog(title = "User Registered", body = message)

        }

        is UiState.Error -> {
            val message = (uiState.value as UiState.Error).errorMessage
            AuthDialog(title = "Error Occured", body = message)

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

            var emailError by remember { mutableStateOf(false)}
            var passwordError by remember { mutableStateOf(false)}

            CustomInputField(
                label = "Name",
                inputText = name,
                modifier = Modifier.width(312.dp),
                onTextChange = { name = it }
            )
            CustomInputField(
                label = "Email",
                inputText = email,
                modifier = Modifier.width(312.dp),
                onTextChange = {
                    email = it
                    emailError = !isValidEmail(email)
                }
            )

            CustomInputField(
                label = "Password",
                inputText = password,
                modifier = Modifier.width(312.dp),
                isPassword = true,
                onTextChange = {
                    password = it
                    passwordError = !isValidPassword(password)
                },
            )
            LongButton(
                text = "Sign Up",
                modifier = Modifier.padding(vertical = 16.dp),
                isError = passwordError || emailError,
                color = MaterialTheme.colorScheme.secondary,
                textColor = MaterialTheme.colorScheme.onSecondary,
                onClick = {
                    Log.d("SignUpScreen", "Sign Up Clicked" )
                    onSignUp(name, email, password)
                }
            )
        }

    }

}

@Preview(showBackground = true)
@Composable
fun SignUpScreenPreview() {
    PatypetTheme {
    }
}