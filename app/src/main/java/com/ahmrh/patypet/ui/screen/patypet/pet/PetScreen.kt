package com.ahmrh.patypet.ui.screen.patypet.pet

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ahmrh.patypet.R
import com.ahmrh.patypet.common.UiState
import com.ahmrh.patypet.data.remote.responses.PetByIdResponse
import com.ahmrh.patypet.ui.components.ChipType
import com.ahmrh.patypet.ui.components.CustomChip
import com.ahmrh.patypet.ui.components.CustomImage
import com.ahmrh.patypet.ui.components.bar.PredictionTopBar
import com.ahmrh.patypet.ui.components.button.CustomButton
import com.ahmrh.patypet.ui.components.card.PredictionCard
import com.ahmrh.patypet.ui.components.dialog.CustomDialog
import com.ahmrh.patypet.ui.components.dialog.SavePetDialog
import com.ahmrh.patypet.ui.components.loading.Loading
import com.google.accompanist.pager.HorizontalPagerIndicator
import kotlinx.coroutines.launch
import java.util.Locale

@Composable
fun PetScreen(
    uiState: State<UiState<PetByIdResponse>> = mutableStateOf(
        UiState.Idle
    ),
    onNavigateToDetail: () -> Unit = {},

) {


    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        when (uiState.value) {
            is UiState.Idle -> {
                // do nothing
            }

            is UiState.Loading -> {
                Loading()
            }

            is UiState.Success -> {
                val prediction =
                    (uiState.value as UiState.Success<PetByIdResponse>).data

                CustomImage(
                    photoUrl = prediction.data?.imageUrl,
                    modifier = Modifier
                        .fillMaxSize()
                )

                PredictionSheet(
                    prediction,
                    prediction.data?.imageUrl,
                    onNavigateToDetail
                )
            }

            is UiState.Error -> {
                val message =
                    (uiState.value as UiState.Error).errorMessage
                CustomDialog(
                    title = "Error Occurred",
                    body = message,
                    onDismiss = {}
                )

            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PredictionSheet(
    prediction: PetByIdResponse,
    photoUrl: String? = null,
    onNavigateToDetail: () -> Unit = { },
) {
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberBottomSheetScaffoldState()
    val sheetState = scaffoldState.bottomSheetState

    var isBookmarked by remember { mutableStateOf(false) }

    var savePetDialogOpen by remember {
        mutableStateOf(false)
    }

    if (sheetState.currentValue == SheetValue.Expanded && sheetState.targetValue == SheetValue.Expanded) {
        Scaffold(
            topBar = {
                PredictionTopBar(
                    onBack = {
                        scope.launch {
                            sheetState.partialExpand()
                        }
                    },
                    onBookmark = {
                        isBookmarked = !isBookmarked
                        savePetDialogOpen = !savePetDialogOpen
                    },
                    isBookmarked = isBookmarked
                )
            },
            containerColor = MaterialTheme.colorScheme.primary

        ) {
            Box(Modifier.padding(it)) {

                if (savePetDialogOpen && !isBookmarked) {
//                    SavePetDialog()
                }
            }
        } // just there so it work
    }

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetPeekHeight = 320.dp,
        sheetDragHandle = {},
        sheetSwipeEnabled = false,
        sheetContent = {
            BottomSheetContent(
                prediction,
                onExpand = {
                    scope.launch {
                        sheetState.expand()
                    }
                },
                sheetState = sheetState,
                photoUrl = photoUrl,
                onNavigateToDetail = onNavigateToDetail
            )
        },

        ) { }
}

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalFoundationApi::class
)
@Composable
fun BottomSheetContent(
    prediction: PetByIdResponse,
    sheetState: SheetState,
    photoUrl: String?,
    onExpand: () -> Unit = {},
    onNavigateToDetail: () -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.92f)
            .padding(
                top = 25.dp,
                start = 25.dp,
                end = 25.dp
            )
    ) {
        //   Prediction Header
        Row(
            verticalAlignment = Alignment.Top
        ) {

            Column(
                modifier = Modifier
                    .weight(1f)
            ) {

                Text(
                    text = prediction.breedData?.breed?.replaceFirstChar {
                        if (it.isLowerCase()) it.titlecase(
                            Locale.getDefault()
                        ) else it.toString()
                    }
                        ?: "Unknown Entity",
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                )
                Text(
                    text = prediction.breedData?.personality
                        ?: "label1, label2, label3",
                    fontWeight = FontWeight.Light,
                    fontSize = 12.sp
                )

            }
            Text(
                text = "${Math.round((prediction.data?.confidence as Double) * 100)}% Accuracy",
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(
                        horizontal = 8.dp,
                        vertical = 6.dp
                    ),
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }

        Spacer(Modifier.height(16.dp))

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
        ) {

            Row(
                horizontalArrangement = Arrangement.spacedBy(
                    8.dp
                ),
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                CustomChip(
                    chipType = ChipType.Age,
                    content = "${prediction.breedData?.lifespan}",
                    modifier = Modifier.weight(1f)
                )
                CustomChip(
                    chipType = ChipType.Weight,
                    content = "${prediction.breedData?.weight}",
                    modifier = Modifier.weight(1f)
                )
                CustomChip(
                    chipType = ChipType.Color,
                    content = "${prediction.breedData?.colours}",
                    modifier = Modifier.weight(1f),
                    colours = listOf(
                        prediction.breedData?.colours?.color1
                            ?: "",
                        prediction.breedData?.colours?.color2
                            ?: "",
                        prediction.breedData?.colours?.color3
                            ?: "",
                    )
                )
            }

            Spacer(Modifier.height(16.dp))

            // see material3 bottomsheetscaffold documentation for more
            if (sheetState.targetValue != SheetValue.Expanded) {

                Text(
                    prediction.breedData?.description
                        ?: stringResource(id = R.string.lorem),
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 4,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Justify,
                    modifier = Modifier.padding(horizontal = 4.dp)

                )

                Spacer(Modifier.height(16.dp))

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
                Text(
                    prediction.breedData?.description
                        ?: stringResource(id = R.string.lorem),
                    style = MaterialTheme.typography.bodyMedium,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Justify,
                    modifier = Modifier.padding(horizontal = 4.dp)

                )

                Spacer(Modifier.height(16.dp))

                val pagerState = rememberPagerState()
                Log.d(
                    "Prediction Content",
                    " asdasd ${prediction.toString()}"
                )

                val cardTitle = listOf(
                    "Body Features",
                    "Grooming"
                )

                val cardContent = listOf(
                    prediction.breedData?.bodyFeatures?.description
                        ?: "",
                    prediction.breedData?.grooming
                        ?: "",
                )

                // Breed Detail


                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    HorizontalPager(
                        pageCount = 2,
                        state = pagerState
                    ) {
                        PredictionCard(
                            onClick = onNavigateToDetail,
                            isButtonThere = false,
                            cardTitle = cardTitle[it],
                            cardContent = cardContent[it],
                            photoUrl = photoUrl,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(end = 8.dp)
                        )
                    }

                    Spacer(
                        modifier = Modifier
                            .height(16.dp)
                    )
                    HorizontalPagerIndicator(
                        modifier = Modifier
                            .padding(bottom = 10.dp),
                        pageCount = 2,
                        pagerState = pagerState,
                        activeColor = MaterialTheme.colorScheme.primary,
                        inactiveColor = MaterialTheme.colorScheme.primaryContainer
                    )
                }


                Spacer(Modifier.size(24.dp))
            }

        }


    }

}

