package com.ahmrh.patypet.ui.screen.patypet.pet

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
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
import coil.compose.rememberImagePainter
import com.ahmrh.patypet.R
import com.ahmrh.patypet.data.remote.responses.PredictionResponse
import com.ahmrh.patypet.domain.state.UiState
import com.ahmrh.patypet.ui.components.LoadingBar
import com.ahmrh.patypet.ui.components.dialog.AuthDialog
import com.ahmrh.patypet.ui.theme.PatypetTheme
import kotlinx.coroutines.launch

@Composable
fun PetPredictionScreen(
    uiState: State<UiState<PredictionResponse>> = mutableStateOf(
        UiState.Idle
    ),
    photoUri: Uri? = null,
    onPredict: (
        img: Any
    ) -> Unit = {},
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = if (photoUri != null)
                rememberImagePainter(photoUri)
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
                LaunchedEffect(key1 = true) {
                    photoUri?.let { uri ->
                        onPredict(uri)
                    }
                }
            }

            is UiState.Loading -> {
                LoadingBar()
            }

            is UiState.Success -> {
                val prediction =
                    (uiState.value as UiState.Success<PredictionResponse>).data
                PredictionSheet(prediction)
            }

            is UiState.Error -> {
                val message =
                    (uiState.value as UiState.Error).errorMessage
                AuthDialog(
                    title = "Error Occured",
                    body = message
                )

            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PredictionSheet(
    prediction: PredictionResponse = PredictionResponse()
) {
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberBottomSheetScaffoldState()

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetPeekHeight = 128.dp,
        sheetContent = {
            Column(
                modifier = Modifier
                    .padding(horizontal=12.dp)
            ){

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = prediction.predictedLabel ?: "Prediction",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .weight(1f)
                    )
                    Text(
                        text = "${String.format("%.${2}f", prediction.confidence)}% Accuracy",
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(MaterialTheme.colorScheme.primary)
                            .padding(horizontal = 8.dp, vertical=6.dp)

                    )
                }
                Column(
                    Modifier
                        .fillMaxWidth(),
                ) {
                    Spacer(Modifier.height(5.dp))
                    Text("We classify this entity as ${prediction.predictedLabel} with ${String.format("%.${2}f", prediction.confidence)}% Accuracy")
                    Spacer(Modifier.height(20.dp))
                    Button(
                        onClick = {
                            scope.launch { scaffoldState.bottomSheetState.partialExpand() }
                        }
                    ) {
                        Text("Click to collapse sheet")
                    }
                }
            }
        }) { innerPadding ->

    }
}

@Preview
@Composable
fun PetPredictionPreview() {
    PatypetTheme {
        PetPredictionScreen()
        PredictionSheet()
    }
}