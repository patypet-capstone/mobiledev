package com.ahmrh.patypet.ui.components.card

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.ahmrh.patypet.R
import com.ahmrh.patypet.ui.theme.PatypetTheme
import java.text.NumberFormat
import java.util.Locale

@Composable
fun ProductCard(
    photoUri: Uri? = null,
    photoUrl: String? = null,
    name: String = "Concept For Life",
    price: Double = 86000.00,
    onCardClicked: () -> Unit = {}
) {
    Card(
        Modifier
            .width(120.dp)
            .height(161.dp)
            .clickable { onCardClicked() },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.elevatedCardElevation(),
    ) {
        Column {
            if(photoUrl != null){
                AsyncImage(
                    model = photoUrl,
                    contentDescription = null
                )
            }
            else {
                Image(
                    painter = if (photoUri != null)
                        rememberAsyncImagePainter(photoUri) else painterResource(
                        id = R.drawable.placeholder_prediction_image
                    ),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentScale = ContentScale.Crop
                )

            }
            Column(
                modifier = Modifier
                    .height(48.dp)
                    .padding(
                        vertical = 6.dp,
                        horizontal = 8.dp
                    ),
            ) {
                Text(
                    name,
                    fontSize = 12.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Clip
                )
                Text(
                    "Rp ${
                        NumberFormat.getNumberInstance(
                            Locale.US
                        ).format(price)
                    }.00",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Clip
                )

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProductCardPreview() {
    PatypetTheme {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ProductCard()
        }
    }
}