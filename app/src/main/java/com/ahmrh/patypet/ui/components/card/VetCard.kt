package com.ahmrh.patypet.ui.components.card

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ahmrh.patypet.ui.components.CustomImage
import com.ahmrh.patypet.ui.theme.PatypetTheme

@Composable
fun VetCard(
    photoUrl: String? = null,
    onContact: () -> Unit = {},
    name: String ="Drh. Adelina Porawouw M.Si",
    location: String = "Setiabudi, Jakarta Selatan"

    ) {

    ElevatedCard() {

        Column(
            modifier = Modifier
                .height(156.dp)
                .fillMaxWidth()
        ) {
            Row {

                CustomImage(
                    modifier = Modifier.size(
                        width = 108.dp,
                        height = 156.dp
                    ),
                    photoUrl = photoUrl,
                )
                Box(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxHeight()
                        .weight(1f)
                ) {
                    Column {

                        Text(
                            name,
                            color = MaterialTheme.colorScheme.onSurface,
                            maxLines = 1,
                            overflow = TextOverflow.Clip,
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(Modifier.size(8.dp))
                        Row(){

                            Icon(imageVector = Icons.Default.LocationOn, contentDescription = null)
                            Text(
                                location,
                                color = MaterialTheme.colorScheme.onSurface,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }

                    }
                    Button(
                        onClick = {}, modifier = Modifier.align(
                            Alignment.BottomEnd
                        )
                    ) {
                        Text("Contact ")
                        Icon(
                            imageVector = Icons.Default.Phone,
                            contentDescription = null,
                            Modifier.size(16.dp)
                        )

                    }
                }


            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun VetCardPreview() {
    PatypetTheme() {
        VetCard()
    }
}