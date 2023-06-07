package com.ahmrh.patypet.ui.components

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ahmrh.patypet.R
import com.ahmrh.patypet.ui.theme.PatypetTheme

@Composable
fun CustomCard(
    modifier: Modifier = Modifier,
    photoUri: Uri? = null,
    onClick: () -> Unit = {},
    isButtonThere: Boolean = true
) {
    Row(
        modifier = modifier
            .height(168.dp)
            .width(312.dp)
            .clip(shape = RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.primary)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .weight(2f),
            verticalArrangement = Arrangement.spacedBy(9.dp)
        ) {
            Text(
                "Card Title",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.secondary
            )
            Text(
                "this should be a place for card content. but since there is no text, this should be suffice for a placeholder.",
                fontSize = 10.sp,
                color = MaterialTheme.colorScheme.secondary,
                lineHeight = 16.sp
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
            painterResource(id = R.drawable.placeholder_prediction_image),
            contentDescription = null,
            Modifier
                .width(112.dp)
                .height(168.dp)

        )

    }

}

@Preview(showBackground = true)
@Composable
fun CustomCardPreview() {
    PatypetTheme {
        CustomCard()
    }
}