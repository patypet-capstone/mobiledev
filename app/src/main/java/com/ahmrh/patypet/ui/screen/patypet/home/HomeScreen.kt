package com.ahmrh.patypet.ui.screen.patypet.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ahmrh.patypet.common.AuthState
import com.ahmrh.patypet.common.UiState
import com.ahmrh.patypet.data.remote.responses.ArticleResponseItem
import com.ahmrh.patypet.data.remote.responses.PetResponse
import com.ahmrh.patypet.domain.model.User
import com.ahmrh.patypet.ui.components.Feature
import com.ahmrh.patypet.ui.components.FeatureButton
import com.ahmrh.patypet.ui.components.card.ArticleCard
import com.ahmrh.patypet.ui.components.card.LoadingArticle
import com.ahmrh.patypet.ui.components.card.LoadingPetCard
import com.ahmrh.patypet.ui.components.card.PetCard
import com.ahmrh.patypet.ui.components.loading.Loading
import com.ahmrh.patypet.ui.theme.PatypetTheme
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    deauthenticate: () -> Unit = {},
    articleUiState: State<UiState<List<ArticleResponseItem>>>,
    petUiState: State<UiState<PetResponse>>,
    authState: AuthState,
    user: User,
    navigateToShop: () -> Unit = {},
    navigateToCaretake: () -> Unit = {},
    navigateToVet: () -> Unit = {},
    navigateToAdopt: () -> Unit = {},


) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        val snackbarHostState =
            remember { SnackbarHostState() }
        val scope = rememberCoroutineScope()

        when(petUiState.value){
            is UiState.Loading -> {
                Loading()

            }
            else -> {

                Scaffold(
                    snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.surface)

                ) {

                    Column(
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.surface)
                            .fillMaxSize()
                            .verticalScroll(
                                rememberScrollState()
                            )
                            .padding(it)
                            .padding(24.dp),
                        verticalArrangement = Arrangement.spacedBy(
                            16.dp
                        ),
                    ) {

                        Row {
                            Text(
                                text = "Hello, ",
                                fontSize = 24.sp,

                                )

                            Text(
                                text = user.name ?: "...",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }


                        HomeFeatureSection(
                            navigateToShop = navigateToShop,
                            navigateToCaretake = navigateToCaretake,
                            navigateToVet = navigateToVet,
                            navigateToAdopt = navigateToAdopt,
                            onClickUnderDevelopment = {
                                scope.launch {
                                    snackbarHostState.showSnackbar(
                                        message = "Feature still under development",
                                        duration = SnackbarDuration.Short
                                    )
                                }
                            }
                        )

                        HomeMyPetSection(petUiState, onClickUnderDevelopment = {
                            scope.launch {
                                snackbarHostState.showSnackbar(
                                    message = "Feature still under development",
                                    duration = SnackbarDuration.Short
                                )
                            }
                        })

                        HomeArticleSection(articleUiState)
                    }
                }
            }
        }




    }

}

@Composable
fun HomeFeatureSection(
    navigateToShop: () -> Unit,
    navigateToCaretake: () -> Unit,
    navigateToVet: () -> Unit,
    navigateToAdopt: () -> Unit,

    onClickUnderDevelopment: () -> Unit
) {

    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        FeatureButton(
            featureType = Feature.Shop,
            onClick = navigateToShop
        )
        FeatureButton(
            featureType = Feature.Caretake,
            onClick = onClickUnderDevelopment
//            onClick = navigateToCaretake
        )
        FeatureButton(
            featureType = Feature.Vet,
            onClick = onClickUnderDevelopment
//            onClick = navigateToVet
        )
        FeatureButton(
            featureType = Feature.Adopt,
            onClick = onClickUnderDevelopment
//            onClick = navigateToAdopt
        )
    }
}

@Composable
fun HomeMyPetSection(
    petUiState: State<UiState<PetResponse>>,
    onClickUnderDevelopment: () -> Unit
) {


    when (petUiState.value) {
        is UiState.Idle -> {
            Text(
                text = "My Pet",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(4.dp))
            LoadingPetCard()
        }

        is UiState.Loading -> {
            Text(
                text = "My Pet",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(4.dp))
            LoadingPetCard()
        }

        is UiState.Success -> {

            Text(
                text = "My Pet",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.fillMaxWidth()
            )


            val pets =
                (petUiState.value as UiState.Success<PetResponse>).data.data
                    ?: listOf()

            LazyRow(
                horizontalArrangement = Arrangement
                    .spacedBy(10.dp),
            ) {
                items(pets) { pet ->


                    PetCard(
                        photoUrl = pet?.imageUrl,
                        name = (pet?.name ?: "Unnamed Entity").toString(),
                        breed = pet?.predictedLabel,
                        onClick = onClickUnderDevelopment
                    )

                }
            }
        }

        is UiState.Error -> {
        }
    }

//    Row(
//        horizontalArrangement = Arrangement
//            .spacedBy(10.dp),
//        modifier = Modifier
//            .horizontalScroll(rememberScrollState())
//
//    ) {
//        PetCard()
//        PetCard()
//        PetCard()
//        PetCard()
//    }

}

@Composable
fun HomeArticleSection(
    articleUiState: State<UiState<List<ArticleResponseItem>>>,
) {
    Spacer(Modifier.height(4.dp))

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