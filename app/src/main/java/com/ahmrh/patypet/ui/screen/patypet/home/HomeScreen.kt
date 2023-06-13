package com.ahmrh.patypet.ui.screen.patypet.home

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ahmrh.patypet.data.remote.responses.ArticleResponseItem
import com.ahmrh.patypet.data.remote.responses.PetResponse
import com.ahmrh.patypet.domain.state.UiState
import com.ahmrh.patypet.ui.components.Feature
import com.ahmrh.patypet.ui.components.FeatureButton
import com.ahmrh.patypet.ui.components.card.ArticleCard
import com.ahmrh.patypet.ui.components.card.LoadingArticle
import com.ahmrh.patypet.ui.components.card.PetCard
import com.ahmrh.patypet.ui.theme.PatypetTheme

@Composable
fun HomeScreen(
    deauthenticate: () -> Unit = {},
    articleUiState: State<UiState<List<ArticleResponseItem>>>,
    petUiState: State<UiState<PetResponse>> = mutableStateOf(
        UiState.Idle
    ),

    ) {
    Surface(
        modifier = Modifier
            .fillMaxHeight()
    ) {

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text(
                text = "Hello, User",
                fontSize = 24.sp,
                modifier = Modifier.fillMaxWidth()

            )

            val spaceValue = 4.dp

            HomeFeatureSection()

            Spacer(Modifier.height(spaceValue))
            HomeMyPetSection()

            Spacer(Modifier.height(spaceValue))
            HomeArticleSection(articleUiState)
        }


    }

}

@Composable
fun HomeFeatureSection() {

    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        FeatureButton(featureType = Feature.Shop)
        FeatureButton(featureType = Feature.Caretake)
        FeatureButton(featureType = Feature.Vet)
        FeatureButton(featureType = Feature.Adopt)
    }
}

@Composable
fun HomeMyPetSection() {
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

    ) {
        PetCard()
        PetCard()
        PetCard()
        PetCard()
    }

}

@Composable
fun HomeArticleSection(
    articleUiState: State<UiState<List<ArticleResponseItem>>>,
) {
    Text(
        text = "Article",
        fontSize = 16.sp,
        fontWeight = FontWeight.Medium,
        modifier = Modifier.fillMaxWidth()
    )
    when (articleUiState.value) {
        is UiState.Idle -> {
            LoadingArticle()
        }

        is UiState.Loading -> {
            LoadingArticle()
        }

        is UiState.Success -> {
            val articles =
                (articleUiState.value as UiState.Success<List<ArticleResponseItem>>).data
                    ?: listOf()

            val uriHandler = LocalUriHandler.current

            LazyRow(
                horizontalArrangement = Arrangement
                    .spacedBy(10.dp),
            ) {
                items(articles) { article ->
                    ArticleCard(
                        photoUrl = article.imageUrl,
                        title = article?.title ?: "",
                        author = "Author",
                        date = "Juni 12th 2023",
                        onClick = {
                            article.url?.let {
                                uriHandler.openUri(
                                    it
                                )
                            }

                        })
                }
            }
        }

        is UiState.Error -> {
            LoadingArticle()
        }
    }
}


@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    PatypetTheme() {
//        HomeScreen()
    }
}