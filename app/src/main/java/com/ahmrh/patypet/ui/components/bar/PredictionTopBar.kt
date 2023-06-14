package com.ahmrh.patypet.ui.components.bar

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ahmrh.patypet.R
import com.ahmrh.patypet.ui.theme.PatypetTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PredictionTopBar(
    onBack: () -> Unit = {},
    onBookmark: () -> Unit = {},
    isActionThere: Boolean = true,
    title: String? = null
){

    TopAppBar(
        title = {
            Text(
                title ?: "Prediction",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                fontWeight = FontWeight.Bold

                )
        },
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(
                    painter = painterResource(id = R.drawable.arrow_back),
                    contentDescription = "Localized description",
                    modifier = Modifier
                        .size(24.dp)
                )
            }
        },
        actions = {
                  if(isActionThere){

                      IconButton(onClick = {
                          onBookmark
                      }) {
                          Icon(
                              painter = painterResource(id = R.drawable.bookmark),
                              contentDescription = "Localized description",
                              modifier = Modifier
                                  .size(24.dp)
                          )
                      }
                  } else{

                      IconButton(onClick = {
                      }) {
                      }
                  }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            actionIconContentColor = MaterialTheme.colorScheme.onPrimary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
        )
    )
}

@Preview(showBackground = true)
@Composable
fun PredictionTopBarPreview(){
    PatypetTheme(darkTheme = true){
        PredictionTopBar()
    }

}
