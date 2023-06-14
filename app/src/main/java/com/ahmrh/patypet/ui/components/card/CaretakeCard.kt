package com.ahmrh.patypet.ui.components.card

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ahmrh.patypet.R
import com.ahmrh.patypet.ui.components.CustomImage
import com.ahmrh.patypet.ui.theme.PatypetTheme

@Composable
fun CaretakeCard() {

    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        CustomImage(
            modifier = Modifier
                .width(96.dp)
                .height(136.dp)
                .clip(RoundedCornerShape(8.dp))
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(
                        4.dp
                    ),
                ) {

                    CaretakeChip("Dog")
                }

                IconButton(onClick = {}, modifier = Modifier.align(Alignment.TopEnd).size(24.dp)) {
                    Icon(
                        imageVector = Icons.Default.FavoriteBorder,
                        contentDescription = null,
                    )
                }

            }

            Spacer(Modifier.size(8.dp))

            Text("Sunny Side Caretaker", fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.size(8.dp))
            Row(){
                Icon(imageVector = Icons.Default.LocationOn, contentDescription = null)
                Text("Jakarta Selatan")
            }

            Spacer(Modifier.size(24.dp))

            Box(
                modifier = Modifier.fillMaxWidth()
            ){

                Text("Rp 50.000 / Hari", Modifier.align(
                    Alignment.BottomEnd))
            }

        }

    }
}

@Composable
fun CaretakeChip(
    content: String = "Test"
) {

    Box(
        modifier = Modifier
            .border(
                0.4.dp,
                MaterialTheme.colorScheme.onSurface,
                RoundedCornerShape(4.dp)
            )
            .padding(vertical = 2.dp, horizontal = 4.dp)
    ) {
        Text(text = content)
    }
}

@Preview(showBackground = true)
@Composable
fun CaretakeCardPreview() {
    PatypetTheme {
        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(
                    rememberScrollState()
                )
                .padding(
                    horizontal = 12.dp,
                    vertical = 12.dp
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp),


            ) {

            CaretakeCard()
            CaretakeCard()
            CaretakeCard()
        }
    }
}