package com.ahmrh.patypet.ui.screen.patypet.pet

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.rememberImagePainter
import com.ahmrh.patypet.data.remote.responses.PredictionResponse
import com.ahmrh.patypet.domain.state.UiState
import com.ahmrh.patypet.ui.components.LoadingBar
import com.ahmrh.patypet.ui.components.dialog.AuthDialog
import kotlin.reflect.KFunction1

@Composable
fun PetPredictionScreen(
    uiState: State<UiState<PredictionResponse>>,
    photoUri: Uri? = null,
    onPredict: (
        img: Any
    ) -> Unit,
){
    Image(
        painter = rememberImagePainter(photoUri),
        contentDescription = null,
        modifier = Modifier.fillMaxSize()
    )
    when(uiState.value){
        is UiState.Idle -> {
            LaunchedEffect(key1 = true){
                photoUri?.let {
                    onPredict(it)
                }
            }
        }
        is UiState.Loading -> {
            LoadingBar()
        }
        is UiState.Success -> {
            val prediction = (uiState.value as UiState.Success<PredictionResponse>).data
            Text(prediction.toString())
        }

        is UiState.Error -> {
            val message = (uiState.value as UiState.Error).errorMessage
            AuthDialog(title = "Error Occured", body = message)

        }
    }

}

@Preview
@Composable
fun PetPredictionPreview(){
}