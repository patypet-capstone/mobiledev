package com.ahmrh.patypet.ui.screen.patypet.pet.prediction_detail

import android.net.Uri
import android.util.Log
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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.ahmrh.patypet.R
import com.ahmrh.patypet.data.remote.responses.PredictionResponse
import com.ahmrh.patypet.ui.components.ChipType
import com.ahmrh.patypet.ui.components.CustomChip
import com.ahmrh.patypet.ui.components.button.CustomButton
import com.ahmrh.patypet.ui.components.card.PredictionCard
import com.ahmrh.patypet.ui.theme.PatypetTheme
import com.google.accompanist.pager.HorizontalPagerIndicator
import kotlinx.coroutines.launch

@Composable
fun PetPredictionDetailScreen(

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

        PredictionDetailSheet(prediction, photoUri, onNavigateUp)


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
            TopAppBar(
                title = {
                    Text(
                        "Simple TopAppBar",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateUp) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Localized description"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* doSomething() */ }) {
                        Icon(
                            imageVector = Icons.Filled.Favorite,
                            contentDescription = "Localized description"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    actionIconContentColor = MaterialTheme.colorScheme.secondary,
                    titleContentColor = MaterialTheme.colorScheme.secondary,
                    navigationIconContentColor = MaterialTheme.colorScheme.secondary,
                )
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
            .padding(vertical = 23.dp)
            .fillMaxWidth()
            .fillMaxHeight(0.9f)
    ) {

        Column(
            modifier = Modifier
                .padding(25.dp)
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
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Spacer(Modifier.height(16.dp))

                PredictionCard(isButtonThere = false, onClick = {}, photoUri = photoUri, modifier = Modifier.fillMaxWidth())

                PredictionCard(isButtonThere = false, onClick = {}, photoUri = photoUri, modifier = Modifier.fillMaxWidth())

                PredictionCard(isButtonThere = false, onClick = {}, photoUri = photoUri, modifier = Modifier.fillMaxWidth())

            }
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


