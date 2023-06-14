package com.ahmrh.patypet.ui.components.card

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
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
fun AdoptCard(
    userPhotoUrl: String? = null,
    userName: String,
    petPhotoUrl: String? = null,
    petName:String,
    petBreed: String,
    onAdopt: () -> Unit,
) {

    Column(
        modifier = Modifier.padding(24.dp)
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            CustomImage(

                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape),
                photoUrl = userPhotoUrl
            )
            Spacer(modifier = Modifier.size(12.dp))

            Text(
                userName,
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium
            )
        }


        Spacer(Modifier.size(8.dp))
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {

            CustomImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(256.dp)
                    .clip(RoundedCornerShape(8.dp)),
                photoUrl = petPhotoUrl
            )
        }
        Spacer(Modifier.size(16.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column {

                Text(
                    petName,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.size(8.dp))
                Text(
                    petBreed,
                    fontSize = 16.sp,
                )
            }

            Button(
                onClick = {},
//                    colors = IconButtonDefaults.iconButtonColors(
//                        containerColor = MaterialTheme.colorScheme.primary,
//                        contentColor = MaterialTheme.colorScheme.onPrimary
//                    ),
            ) {
                Text("Adopt")
                Spacer(Modifier.size(8.dp))
                Icon(
                    painter= painterResource(id = R.drawable.splash_logo_patypet),
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun AdoptPostPreview() {
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
//
//            AdoptCard()
//            AdoptCard()
//            AdoptCard()
        }
    }
}