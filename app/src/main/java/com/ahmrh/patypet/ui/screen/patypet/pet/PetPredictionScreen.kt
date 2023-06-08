package com.ahmrh.patypet.ui.screen.patypet.pet

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.ahmrh.patypet.R
import com.ahmrh.patypet.data.remote.responses.PredictionResponse
import com.ahmrh.patypet.domain.state.UiState
import com.ahmrh.patypet.ui.components.ChipType
import com.ahmrh.patypet.ui.components.CustomChip
import com.ahmrh.patypet.ui.components.button.CustomButton
import com.ahmrh.patypet.ui.components.card.PredictionCard
import com.ahmrh.patypet.ui.components.dialog.CustomDialog
import com.ahmrh.patypet.ui.components.loading.PredictionLoading
import com.ahmrh.patypet.ui.theme.PatypetTheme
import com.google.accompanist.pager.HorizontalPagerIndicator
import kotlinx.coroutines.launch

@Composable
fun PetPredictionScreen(
    uiState: State<UiState<PredictionResponse>> = mutableStateOf(
        UiState.Idle
    ),
    photoUri: Uri? = null,
    onPredict: (img: Any) -> Unit = {},
    onRetakePhoto: () -> Unit = {},
) {

    LaunchedEffect(key1 = true) {
        photoUri?.let { uri ->
            onPredict(uri)
        }
    }
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = if (photoUri != null)
                rememberAsyncImagePainter(photoUri)
            else painterResource(
                id = R.drawable.cat
            ),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        when (uiState.value) {
            is UiState.Idle -> {
                // do nothing
            }

            is UiState.Loading -> {
                PredictionLoading()
            }

            is UiState.Success -> {
                val prediction =
                    (uiState.value as UiState.Success<PredictionResponse>).data
                PredictionSheet(prediction, photoUri)
            }

            is UiState.Error -> {
                val message =
                    (uiState.value as UiState.Error).errorMessage
                CustomDialog(
                    title = "Error Occurred",
                    body = message,
                    onDismiss = onRetakePhoto
                )

            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PredictionSheet(
    prediction: PredictionResponse = PredictionResponse(),
    photoUri: Uri? = null,
) {
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberBottomSheetScaffoldState()

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetPeekHeight = 360.dp,
        sheetContent = {
            BottomSheetContent(
                prediction,
                onExpand = {
                    scope.launch {
                        scaffoldState.bottomSheetState.expand()
                    }
                },
                sheetState = scaffoldState.bottomSheetState,
                photoUri = photoUri
            )
        }
    ) {

    }
}

@OptIn(ExperimentalMaterial3Api::class,
       ExperimentalFoundationApi::class
)
@Composable
fun BottomSheetContent(
    prediction: PredictionResponse = PredictionResponse(),
    sheetState: SheetState,
    photoUri: Uri?,
    onExpand: () -> Unit = {}
) {

    Column(
        modifier = Modifier
            .padding(horizontal = 25.dp)
            .fillMaxSize()
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = prediction.predictedLabel
                    ?: "British Short  Hair",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                modifier = Modifier
                    .weight(1f)
            )
            Text(
                text = "${
                    String.format(
                        "%.${2}f",
                        prediction.confidence
                    )
                } Accuracy",
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(
                        horizontal = 8.dp,
                        vertical = 6.dp
                    ),
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp,

                )
        }
        Column(
            Modifier
                .fillMaxWidth(),
        ) {
            Spacer(Modifier.height(16.dp))

            Text(
                prediction.breedData?.description
                    ?: "Unidentified Breed",
                style = MaterialTheme.typography.bodyMedium
            )
//                    Text(prediction.breedData?.description ?: "Unidentified Breed")

            Spacer(Modifier.height(9.dp))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                CustomChip(
                    chipType = ChipType.Age,
                    content = "${prediction.breedData?.lifespan}"
                )
                CustomChip(
                    chipType = ChipType.Weight,
                    content = "${prediction.breedData?.lifespan}"
                )
                CustomChip(
                    chipType = ChipType.Color,
                    content = "${prediction.breedData?.lifespan}"
                )
            }

            Spacer(Modifier.height(25.dp))

            // see material3 bottomsheetscaffold documentation for more
            if(sheetState.targetValue != SheetValue.Expanded){
                CustomButton(
                    text = "Read More",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp),
                    color = MaterialTheme.colorScheme.secondary,
                    textColor = MaterialTheme.colorScheme.onSecondary,
                    onClick = onExpand
                )
            } else {

                val pagerState = rememberPagerState()
                Log.d("Prediction Content", " asdasd ${prediction.toString()}")

                val cardTitle = listOf(
                    "Body Features",
                    "Health",
                    "Grooming"
                )

                val cardContent = listOf(
                    prediction.breedData?.bodyFeatures ?: "",
                    prediction.breedData?.healthConditions ?: "",
                    prediction.breedData?.grooming ?: "",
                )


                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    HorizontalPager(pageCount = 3, state = pagerState) {
                        PredictionCard(
                            onClick = {},
                            isButtonThere = false,
                            cardTitle = cardTitle[it],
                            cardContent = cardContent[it],
                            photoUri = photoUri
                        )
                    }
                    Spacer(modifier = Modifier
                        .height(16.dp))
                    HorizontalPagerIndicator(
                        modifier = Modifier
                            .padding(bottom = 10.dp),
                        pageCount = 3,
                        pagerState = pagerState,
                        activeColor = MaterialTheme.colorScheme.primary,
                        inactiveColor = MaterialTheme.colorScheme.primaryContainer
                    )
                }
            }
        }

    }
}


@Preview
@Composable
fun PetPredictionPreview() {
    PatypetTheme {
        PetPredictionScreen {

        }
        PredictionSheet()
    }
}