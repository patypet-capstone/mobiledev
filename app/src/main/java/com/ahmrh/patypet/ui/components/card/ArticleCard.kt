package com.ahmrh.patypet.ui.components.card

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush.Companion.linearGradient
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.ahmrh.patypet.R
import com.ahmrh.patypet.ui.theme.PatypetTheme

@Composable
fun ArticleCard(
    photoUrl: String? = null,
    title: String = "Why dog is better than cat?",
    author: String = "Abigail",
    date: String = "May 17th 2023",
    onClick: () -> Unit = {}
) {
    Card(
        Modifier
            .width(240.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Column {
            if (photoUrl != null) {
                AsyncImage(
                    model = photoUrl,
                    contentDescription = null,

                    modifier = Modifier
                        .fillMaxWidth()
                        .height(132.dp),
                    contentScale = ContentScale.Crop
                )
            } else {
                Image(
                    painter = if (photoUrl != null)
                        rememberAsyncImagePainter(photoUrl) else painterResource(
                        id = R.drawable.placeholder_prediction_image
                    ),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(132.dp),
                    contentScale = ContentScale.Crop
                )
            }

            Column(
                modifier = Modifier
                    .height(96.dp)
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    title,
                    style = MaterialTheme.typography.titleSmall,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Row {
                    Text(
                        author,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        date,
                        style = MaterialTheme.typography.bodySmall
                    )
                }

            }
        }
    }
}

@Composable
fun LoadingArticle() {
    val loadingColor = MaterialTheme.colorScheme.surfaceVariant

    Card(
        Modifier
            .width(240.dp)
            .height(228.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Column {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(132.dp)
                    .background(loadingColor),
            )
            Column(
                modifier = Modifier
                    .height(96.dp)
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween,
            ) {
//                Text(
//                    "test",
//                    style = MaterialTheme.typography.titleSmall,
//                    maxLines = 2,
//                    overflow = TextOverflow.Ellipsis
//                )
                Spacer(
                    modifier = Modifier
                        .background(loadingColor)
                        .fillMaxWidth()
                        .height(20.dp)
                )
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
//                    Text(
//                        "author",
//                        style = MaterialTheme.typography.bodySmall,
//                        modifier = Modifier.weight(1f)
//                    )
                    Spacer(
                        modifier = Modifier
                            .background(loadingColor)
                            .width(56.dp)
                            .height(20.dp)
                    )
//                    Text(
//                        "date",
//                        style = MaterialTheme.typography.bodySmall
//                    )
                    Spacer(
                        modifier = Modifier
                            .background(loadingColor)
                            .width(96.dp)
                            .height(20.dp)
                    )
                }

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoadingArticlePreview() {
    PatypetTheme {
        LoadingArticle()
    }
}

@Preview(showBackground = true)
@Composable
fun ArticleCardPreview() {
    PatypetTheme {
        ArticleCard()
    }
}
