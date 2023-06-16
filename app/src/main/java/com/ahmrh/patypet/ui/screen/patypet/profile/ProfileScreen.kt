package com.ahmrh.patypet.ui.screen.patypet.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.ahmrh.patypet.ui.theme.PatypetTheme

@Composable
fun ProfileScreen(
    deauthenticate: () -> Unit = {}
){

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {

        Button(
            onClick = deauthenticate,
            modifier = Modifier
                .align(Alignment.Center)
        ) {
            Text("Logout")
        }

    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview(){
    PatypetTheme {
        ProfileScreen()
    }
}