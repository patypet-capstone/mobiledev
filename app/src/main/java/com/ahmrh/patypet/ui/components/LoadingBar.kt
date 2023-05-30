package com.ahmrh.patypet.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ahmrh.patypet.ui.theme.PatypetTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun LoadingBar(
    modifier : Modifier = Modifier
){
    Box (
        modifier =
            modifier.fillMaxSize()
    ){
        CircularProgressIndicator(
            modifier = Modifier
                .align(Alignment.Center)
                .size(56.dp)
            ,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LoadingBarPreview(){
    PatypetTheme() {
        LoadingBar()
    }
}