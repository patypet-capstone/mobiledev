package com.ahmrh.patypet.ui.components.card

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.ahmrh.patypet.R
import com.ahmrh.patypet.ui.theme.PatypetTheme

@Composable
fun PredictionCard(
    modifier: Modifier = Modifier,
    photoUri: Uri? = null,
    onClick: () -> Unit,
    cardTitle: String = "Card Title",
    cardContent: String = "this should be a place for card content. but since there is no text, this should be suffice for a placeholder.",
    isButtonThere: Boolean
) {
    Row(
        modifier = modifier
            .height(168.dp)
            .width(312.dp)
            .clip(shape = RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.primary)
            .clickable{
                onClick()
            }
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .weight(2f),
            verticalArrangement = Arrangement.spacedBy(9.dp)
        ) {
            Text(
                cardTitle,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onPrimary
            )
            Text(
                cardContent,
                fontSize = 10.sp,
                color = MaterialTheme.colorScheme.onPrimary,
                lineHeight = 16.sp,
                maxLines = 6
            )

            if (isButtonThere) {

                Button(
                    onClick = onClick,
                    contentPadding = PaddingValues(
                        vertical = 5.dp,
                        horizontal = 9.dp
                    ),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary,
                        contentColor = MaterialTheme.colorScheme.onSecondary
                    ),
                    shape = RoundedCornerShape(8.dp)

                ) {
                    Text(
                        fontSize = 10.sp,
                        text = "Click to see more",
                        color = MaterialTheme.colorScheme.onSecondary,
                        textAlign = TextAlign.Center
                    )
                }
            }

        }
        Image(

            painter = if (photoUri != null)
                rememberAsyncImagePainter(photoUri) else painterResource(
                id = R.drawable.placeholder_prediction_image
            ),
            contentDescription = null,
            Modifier
                .width(112.dp)
                .height(168.dp),
            contentScale = ContentScale.Crop,

            )

    }

}

@Preview(showBackground = true)
@Composable
fun PredictionCardPreview() {
    PatypetTheme {
        PredictionCard(
            isButtonThere = false,
            onClick = {}
        )
    }
}