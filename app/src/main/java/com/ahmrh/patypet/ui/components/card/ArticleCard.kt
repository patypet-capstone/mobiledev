package com.ahmrh.patypet.ui.components.card

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ahmrh.patypet.R
import com.ahmrh.patypet.ui.theme.PatypetTheme

@Composable
fun ArticleCard(
    photoUri: Uri? = null,
    title: String = "Why dog is better than cat?",
    author: String = "Abigail",
    date: String = "May 17th 2023"
) {
    Card(
        Modifier.size(width = 240.dp, height = 216.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        elevation =  CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Column{
            Image(
                painter = painterResource(id = R.drawable.placeholder_prediction_image),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .height(84.dp)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ){
                Text(title, style = MaterialTheme.typography.titleSmall)
                Row{
                    Text(author, style = MaterialTheme.typography.bodySmall, modifier = Modifier.weight(1f))
                    Text(date, style = MaterialTheme.typography.bodySmall)
                }

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ArticleCardPreview() {
    PatypetTheme {
        ArticleCard()
    }
}
