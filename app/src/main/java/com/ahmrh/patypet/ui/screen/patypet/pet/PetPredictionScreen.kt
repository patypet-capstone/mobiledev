package com.ahmrh.patypet.ui.screen.patypet.pet

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType.Companion.Uri
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.rememberImagePainter

@Composable
fun PetPredictionScreen(
    photoUri: Uri? = null
){
    Image(
        painter = rememberImagePainter(photoUri),
        contentDescription = null,
        modifier = Modifier.fillMaxSize()
    )

}

@Preview
@Composable
fun PetPredictionPreview(){
    PetPredictionScreen(
    )
}