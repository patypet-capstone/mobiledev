package com.ahmrh.patypet.ui.components.loading

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ahmrh.patypet.R
import com.ahmrh.patypet.ui.theme.PatypetTheme
import kotlin.math.roundToLong

@Composable
fun PredictionLoading(
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()
    Box(
        modifier =
        modifier
            .fillMaxSize()
    ) {
        val transition = rememberInfiniteTransition()
        val animationValue by transition.animateFloat(
            initialValue = 1f,
            targetValue = 4f,
            animationSpec = infiniteRepeatable(
                animation = tween(5000),
                repeatMode = RepeatMode.Restart
            )
        )
        if (animationValue.roundToLong() == 4L) {
            PredictingLoadingImage(
                painter = painterResource(id = R.drawable.cat_2),
                modifier = Modifier
                    .align(Alignment.Center),
                "Predicting. . ."
            )
        }
        if (animationValue.roundToLong() == 3L) {
            PredictingLoadingImage(
                painter = painterResource(id = R.drawable.dog_1),
                modifier = Modifier
                    .align(Alignment.Center),
                "Predicting. ."
            )
        }
        if (animationValue.roundToLong() == 2L) {

            PredictingLoadingImage(
                painter = painterResource(id = R.drawable.cat_1),
                modifier = Modifier
                    .align(Alignment.Center),
                "Predicting. ."
            )
        }
        if (animationValue.roundToLong() == 1L) {
            PredictingLoadingImage(
                painter = painterResource(id = R.drawable.dog_2),
                modifier = Modifier
                    .align(Alignment.Center),
                "Predicting."
            )
        }

    }
}

@Composable
fun PredictingLoadingImage(
    painter: Painter,
    modifier: Modifier,
    text: String
) {
    Box(
        modifier = modifier,
    ) {
        Image(
            painter = painter,
            contentDescription = null,
            modifier
                .size(72.dp)
        )
    }

}

@Preview(showBackground = true)
@Composable
fun PredictionLoadingPreview() {
    PatypetTheme() {
        PredictionLoading()
    }
}