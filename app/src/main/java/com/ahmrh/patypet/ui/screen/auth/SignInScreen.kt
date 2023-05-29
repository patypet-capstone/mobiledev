package com.ahmrh.patypet.ui.screen.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ahmrh.patypet.ui.components.AnimationHeader
import com.ahmrh.patypet.ui.components.LongButton
import com.ahmrh.patypet.ui.components.LongInputField
import com.ahmrh.patypet.ui.components.StaticHeader
import com.ahmrh.patypet.ui.theme.PatypetTheme

@Composable
fun SignInScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),

        ) {
        StaticHeader(
            modifier = Modifier
                .height(235.dp),
            type = "Dog"
        )
        SignInForm()
    }

}

@Composable
fun SignInForm() {
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

            LongInputField(label = "Email")
            Column(
                horizontalAlignment = Alignment.End,
            ){
                LongInputField(label = "Password", isPassword = true)
                TextButton(onClick = {},
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
                textColor = MaterialTheme.colorScheme.onSecondary
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