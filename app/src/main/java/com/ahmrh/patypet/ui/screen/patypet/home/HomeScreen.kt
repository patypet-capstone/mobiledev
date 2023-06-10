package com.ahmrh.patypet.ui.screen.patypet.home

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ahmrh.patypet.ui.components.Feature
import com.ahmrh.patypet.ui.components.FeatureButton
import com.ahmrh.patypet.ui.components.card.ArticleCard
import com.ahmrh.patypet.ui.components.card.PetCard
import com.ahmrh.patypet.ui.theme.PatypetTheme

@Composable
fun HomeScreen(
    deauthenticate: () -> Unit = {}
) {
    Surface(
    ){

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(24.dp)
            ,
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text(
                text = "Hello, User",
                fontSize = 24.sp,
                modifier = Modifier.fillMaxWidth()

            )

            HomeFeatureSection()

            HomeMyPetSection()

            HomeArticleSection()
        }



    }

}

@Composable
fun HomeFeatureSection(){

    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .fillMaxWidth()
    ){
        FeatureButton(featureType = Feature.Shop)
        FeatureButton(featureType = Feature.Caretake)
        FeatureButton(featureType = Feature.Vet)
        FeatureButton(featureType = Feature.Adopt)
    }
}

@Composable
fun HomeMyPetSection(){
    Text(
        text = "My Pet",
        fontSize = 16.sp,
        fontWeight = FontWeight.Medium,
        modifier = Modifier.fillMaxWidth()
    )

    Row(
        horizontalArrangement = Arrangement
            .spacedBy(10.dp),
        modifier = Modifier
            .horizontalScroll(rememberScrollState())

    ){
        PetCard()
        PetCard()
        PetCard()
        PetCard()
    }

}

@Composable
fun HomeArticleSection(){
    Text(
        text = "Article",
        fontSize = 16.sp,
        fontWeight = FontWeight.Medium,
        modifier = Modifier.fillMaxWidth()
    )
    Row(
        horizontalArrangement = Arrangement
            .spacedBy(10.dp),

        modifier = Modifier
            .horizontalScroll(rememberScrollState())
    ){
        ArticleCard()
        ArticleCard()
        ArticleCard()
    }

}


@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    PatypetTheme() {
        HomeScreen()
    }
}