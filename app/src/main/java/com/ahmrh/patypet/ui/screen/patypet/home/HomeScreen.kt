package com.ahmrh.patypet.ui.screen.patypet.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ahmrh.patypet.ui.components.Feature
import com.ahmrh.patypet.ui.components.FeatureButton
import com.ahmrh.patypet.ui.theme.PatypetTheme

@Composable
fun HomeScreen(
    deauthenticate: () -> Unit = {}
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        Button(
            onClick = deauthenticate,
            modifier = Modifier
                .align(Alignment.Center)
        ) {
            Text("Logout")
        }

    }

    Column(
        modifier = Modifier
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Hello, User",
            fontSize = 24.sp,
            modifier = Modifier.fillMaxWidth()

        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxWidth()
        ){
            FeatureButton(featureType = Feature.Shop)
            FeatureButton(featureType = Feature.Shop)
            FeatureButton(featureType = Feature.Shop)
            FeatureButton(featureType = Feature.Shop)
        }
    }
}



@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    PatypetTheme() {
        HomeScreen()
    }
}