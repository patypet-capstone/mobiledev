package com.ahmrh.patypet.ui.screen.patypet.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.ahmrh.patypet.ui.theme.PatypetTheme

@Composable
fun HomeScreen(
    onLogout : () -> Unit = {}
){
    Box(
        modifier = Modifier.fillMaxSize()
    ){

        Button(
            onClick = onLogout,
            modifier = Modifier
                .align(Alignment.Center)
        ){
            Text("Logout")
        }

    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview(){
    PatypetTheme() {
        HomeScreen()
    }
}