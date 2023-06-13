package com.ahmrh.patypet.ui.screen.patypet.pet.prediction

import android.net.Uri
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
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
import com.ahmrh.patypet.R
import com.ahmrh.patypet.data.remote.responses.PredictionResponse
import com.ahmrh.patypet.common.UiState
import com.ahmrh.patypet.ui.components.bar.PredictionTopBar
import com.ahmrh.patypet.ui.components.card.PredictionCard
import com.ahmrh.patypet.ui.components.dialog.CustomDialog
import com.ahmrh.patypet.ui.components.loading.PredictionLoading
import com.ahmrh.patypet.ui.theme.PatypetTheme

@Composable
fun PetPredictionDetailScreen(
    uiState: State<UiState<PredictionResponse>> = mutableStateOf(
        UiState.Idle
    ),
    prediction: PredictionResponse = PredictionResponse(),
    photoUri: Uri? = null,
    onNavigateUp: () -> Unit = {}
) {

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
                PredictionDetailSheet(
                    prediction,
                    photoUri,
                    onNavigateUp
                )
            }

            is UiState.Error -> {
                val message =
                    (uiState.value as UiState.Error).errorMessage
                CustomDialog(
                    title = "Error Occurred",
                    body = message,
                )

            }


        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PredictionDetailSheet(
    prediction: PredictionResponse = PredictionResponse(),
    photoUri: Uri? = null,
    onNavigateUp: () -> Unit ={}
) {
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberBottomSheetScaffoldState()
    val sheetState = scaffoldState.bottomSheetState

    Scaffold(
        topBar = {
            PredictionTopBar(
                onBack = onNavigateUp,
                title = "Body Features"
            )
        },
        containerColor = MaterialTheme.colorScheme.primary

    ) {
        Box(Modifier.padding(it)){

        }

    } // just there so it work

    LaunchedEffect(key1 = true){
        sheetState.expand()
    }

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetDragHandle = {},
        sheetPeekHeight = 360.dp,
        sheetContent = {
            DetailBottomSheetContent(
                prediction,
                sheetState = sheetState,
                photoUri = photoUri,
            )
        },
        sheetSwipeEnabled = false,
        modifier = Modifier.fillMaxHeight()
    ) {

    }
}

@OptIn(
    ExperimentalMaterial3Api::class,
)
@Composable
fun DetailBottomSheetContent(
    prediction: PredictionResponse = PredictionResponse(),
    sheetState: SheetState,
    photoUri: Uri?,
    onExpand: () -> Unit = {},
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
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = prediction.breedData?.breed
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
                color = MaterialTheme.colorScheme.onPrimary
                )
        }
        Column(
            Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(Modifier.height(16.dp))

            val body = prediction.breedData?.bodyFeatures?.body ?: "Body data unavailable"
            val ears = prediction.breedData?.bodyFeatures?.ears ?: "Ears data unavailable"
            val headShape = prediction.breedData?.bodyFeatures?.headShape ?: "Head shape data unavailable"

            PredictionCard(isButtonThere = false, onClick = {}, photoUri = photoUri, modifier = Modifier.fillMaxWidth(), cardContent = body, cardTitle = "Body")

            PredictionCard(isButtonThere = false, onClick = {}, photoUri = photoUri, modifier = Modifier.fillMaxWidth(),  cardContent = ears, cardTitle = "Ears")

            PredictionCard(isButtonThere = false, onClick = {}, photoUri = photoUri, modifier = Modifier.fillMaxWidth(),  cardContent = headShape, cardTitle = "Head Shape")

        }
    }

}

@Preview(showBackground = true)
@Composable
fun PredictionDetailPreview(){
    PatypetTheme {
        PetPredictionDetailScreen()
    }
}


