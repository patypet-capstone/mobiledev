package com.ahmrh.patypet.ui.components.loading

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ahmrh.patypet.ui.theme.PatypetTheme

@Composable
fun Loading(
    modifier : Modifier = Modifier
){
    val scope = rememberCoroutineScope()
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
fun LoadingPreview(){
    PatypetTheme() {
        Loading()
    }
}