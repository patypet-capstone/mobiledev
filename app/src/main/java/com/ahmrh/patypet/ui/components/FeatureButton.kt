package com.ahmrh.patypet.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.ahmrh.patypet.R
import com.ahmrh.patypet.ui.theme.PatypetTheme

@Composable
fun FeatureButton(
    featureType: Feature,
    onClick: () -> Unit = {},
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(7.dp),
    ){

        FloatingActionButton(
            onClick = onClick,
            containerColor = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .size(72.dp)

        ) {
            when (featureType) {
                is Feature.Shop -> {
                    Image(
                        painterResource(id = R.drawable.feature_icon_shop),
                        contentDescription = null
                    )
                }

                is Feature.Caretake -> {

                    Image(
                        painterResource(id = R.drawable.feature_icon_caretake),
                        contentDescription = null
                    )
                }

                is Feature.Vet -> {

                    Image(
                        painterResource(id = R.drawable.feature_icon_vet),
                        contentDescription = null
                    )
                }

                is Feature.Adopt -> {

                    Image(
                        painterResource(id = R.drawable.feature_icon_adopt),
                        contentDescription = null
                    )
                }

            }
        }
        when (featureType) {
            is Feature.Shop -> {
                Text("Shop",  color = MaterialTheme.colorScheme.onSurface)
            }

            is Feature.Caretake -> {
                Text("Caretake", color = MaterialTheme.colorScheme.onSurface)
            }

            is Feature.Vet -> {
                Text("Vet",  color = MaterialTheme.colorScheme.onSurface)
            }

            is Feature.Adopt -> {
                Text("Adopt", color = MaterialTheme.colorScheme.onSurface)
            }
        }

    }
}

sealed class Feature() {
    object Shop : Feature()
    object Caretake : Feature()
    object Vet : Feature()
    object Adopt : Feature()
}

@Preview(showBackground = true)
@Composable
fun FeatureButtonPreview() {
    PatypetTheme {
        FeatureButton(Feature.Shop)
    }
}