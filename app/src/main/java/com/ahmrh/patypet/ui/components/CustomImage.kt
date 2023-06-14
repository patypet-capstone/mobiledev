package com.ahmrh.patypet.ui.components

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.ahmrh.patypet.R

@Composable
fun CustomImage(
    modifier: Modifier = Modifier,
    photoUrl : String? = null,
    photoUri: Uri? = null,
){

    if(photoUrl != null){
        AsyncImage(
            model = photoUrl,
            contentDescription = null,

            modifier = modifier,
            contentScale = ContentScale.Crop
        )
    }
    else {
        Image(
            painter = if (photoUri != null)
                rememberAsyncImagePainter(photoUri) else painterResource(
                id = R.drawable.placeholder_prediction_image
            ),
            contentDescription = null,
            modifier = modifier,
            contentScale = ContentScale.Crop
        )

    }
}