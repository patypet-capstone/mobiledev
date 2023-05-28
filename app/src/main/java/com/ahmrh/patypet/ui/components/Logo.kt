package com.ahmrh.patypet.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ahmrh.patypet.R
import com.ahmrh.patypet.ui.theme.Orange10
import com.ahmrh.patypet.ui.theme.Orange35
import com.ahmrh.patypet.ui.theme.Orange5

@Composable
fun Logo(
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Image(
            painter = painterResource(id = R.drawable.patypet_logo),
            contentDescription = "Logo Image",
            modifier = Modifier
                .padding(bottom = 22.dp),
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)

        )
        Text(
            text = "PATYPET",
            color = MaterialTheme.colorScheme.primary,
            fontFamily = FontFamily.SansSerif,
            fontSize = 56.sp,
            fontWeight = FontWeight.W900
        )

    }
}

@Preview(showBackground = true)
@Composable
fun LogoPreview(
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Logo()
    }
}
