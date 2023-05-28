package com.ahmrh.patypet.ui.screen.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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

){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ){
        Logo()
        LongButton(text = "Sign In")
        LongButton(text = "Sign Up")
    }
}

@Preview(showBackground = true)
@Composable
fun LandingScreenPreview() {
    PatypetTheme {
        LandingScreen()
    }
}
