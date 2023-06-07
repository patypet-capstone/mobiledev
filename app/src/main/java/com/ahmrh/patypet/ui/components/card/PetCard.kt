package com.ahmrh.patypet.ui.components.card

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ahmrh.patypet.R

@Preview(showBackground = true)
@Composable
fun PetCard(
    photoUri: Uri? = null,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .width(168.dp)
            .height(210.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable{
                onClick()
            }
    ) {
        Image(
            painterResource(id = R.drawable.placeholder_prediction_image),
            contentDescription = null,
            Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(6.dp),
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(horizontal = 16.dp, vertical = 16.dp),
        ) {
            Text(
                text = "Dijjah Yellow",
                color = MaterialTheme.colorScheme.onPrimary,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp
            )
            Text(
                "Golden Retreiver",
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 11.sp
            )

        }


    }
}

@Composable
fun PetCardText(
    title: String,
    subtitle: String,
    alignment: Alignment
) {
    Column(
    ) {
        Text("Dijjah Yellow")
        Text("Golden Retreiver")

    }

}