package com.ahmrh.patypet.ui.screen.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ahmrh.patypet.ui.components.LongButton
import com.ahmrh.patypet.ui.components.LongInputField
import com.ahmrh.patypet.ui.theme.PatypetTheme

@Composable
fun SignInScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {
        Header()
        SignInForm()
    }

}

@Composable
fun Header() {

}

@Composable
fun SignInForm() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            "Welcome Back",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold
        )
        Text(
            "Sign In with your Account",
            style = MaterialTheme.typography.bodyMedium
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            LongInputField(label = "Email")
            LongInputField(label = "Password")
            LongButton(
                text = "Sign In",
                modifier = Modifier.padding(vertical = 16.dp)
            )
        }

    }

}

@Preview(showBackground = true)
@Composable
fun SignInScreenPreview() {
    PatypetTheme {
        SignInScreen()
    }
}