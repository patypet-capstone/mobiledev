package com.ahmrh.patypet.ui.screen.launch

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.ahmrh.patypet.ui.components.Logo
import com.ahmrh.patypet.ui.theme.PatypetTheme

@Composable
fun LaunchScreen(

){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ){
        Logo()
    }
}

@Preview(showBackground = true)
@Composable
fun LaunchScreenPreview(){
    PatypetTheme {
        LaunchScreen()
    }
}
