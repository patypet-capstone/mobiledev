package com.ahmrh.patypet.ui.screen.patypet.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun ProfileScreen(
    deauthenticate: () -> Unit = {}
){

    Box(
        modifier = Modifier.fillMaxSize()
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