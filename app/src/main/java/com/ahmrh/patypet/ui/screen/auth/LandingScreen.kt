package com.ahmrh.patypet.ui.screen.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ahmrh.patypet.ui.components.Logo
import com.ahmrh.patypet.ui.components.LongButton
import com.ahmrh.patypet.ui.theme.PatypetTheme

@Composable
fun LandingScreen(
    navigateToSignIn: () -> Unit = {},
    navigateToSignUp: () -> Unit = {},
    authorize: () -> Unit = {}
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        Logo()
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .padding(vertical = 48.dp)
        ) {
            LongButton(
                text = "Sign In",
                color = MaterialTheme.colorScheme.secondary,
                textColor = MaterialTheme.colorScheme.onSecondary,
                onClick = navigateToSignIn
            )
            LongButton(
                text = "Sign Up",
                modifier = Modifier
                    .border(
                        2.dp,
                        MaterialTheme.colorScheme.outline,
                        RoundedCornerShape(16.dp)
                    ),
                color = MaterialTheme.colorScheme.surface,
                onClick = navigateToSignUp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LandingScreenPreview() {
    PatypetTheme {
        LandingScreen()
    }
}
