package com.ahmrh.patypet.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ahmrh.patypet.R
import com.ahmrh.patypet.ui.theme.PatypetTheme


@Composable
fun AnimationHeader() {
    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.primary)
            .fillMaxWidth()
            .height(180.dp)
    ){

    }

}

@Composable
fun StaticHeader(
    modifier: Modifier = Modifier,
    type : String = "Cat",
){
    Image(
        painter = if(type == "Cat") painterResource(id = R.drawable.cat) else painterResource(
            id = R.drawable.dog
        ),
        contentDescription = null,
        contentScale = ContentScale.FillWidth,
        modifier = modifier
            .fillMaxWidth()
    )
}

@Preview(showBackground = true)
@Composable
fun Preview(){
    PatypetTheme(){
        StaticHeader()
    }
}