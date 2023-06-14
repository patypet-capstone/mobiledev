package com.ahmrh.patypet.ui.screen.patypet.adopt

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.FloatingActionButtonElevation
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ahmrh.patypet.R
import com.ahmrh.patypet.ui.components.bar.PredictionTopBar
import com.ahmrh.patypet.ui.components.card.AdoptCard
import com.ahmrh.patypet.ui.theme.PatypetTheme

@Composable
fun AdoptScreen() {

    val state =rememberScrollState()
    Log.d("ScrollState", state.value.toString())
    var currentScrollValue by remember{ mutableStateOf(0) }
    var movingScrollValue by remember{ mutableStateOf(0) }
// scrolling state like twitter when scroll down fab gone
    var isScrollingDown by remember{ mutableStateOf(false)}

    Surface(
    ) {
        Box {


            Column(
                Modifier
                    .fillMaxSize()
                    .verticalScroll(state)
                    .padding(
                        bottom = 36.dp,
                    ),

                verticalArrangement = Arrangement.spacedBy(0.dp)
            ) {
//                AdoptTopInput()
                AdoptCard(
                    userName = "John Doe",
                    userPhotoUrl = "https://www.socialketchup.in/wp-content/uploads/2020/05/fi-vill-JOHN-DOE.jpg",
                    petBreed = "Golden Retreiver",
                    petName = "Dijjah Yellow",
                    onAdopt = {}
                )
                Divider(Modifier.fillMaxWidth())
                AdoptCard(
                    userName = "Adam",
                    userPhotoUrl = "https://bvmsports.com/wp-content/uploads/2022/09/Anthony-Adams-Yellow-Suit-Cropped-Credit-@spiceadams95-Facebook-1366x768.jpg",
                    petPhotoUrl = "https://pbs.twimg.com/media/D91jlKgXoAADkV0?format=jpg&name=large",
                    petBreed = "Chihuahua",
                    petName = "Sallie",
                    onAdopt = {}
                )

                Divider(Modifier.fillMaxWidth())
                AdoptCard(
                    userName = "Juan",
                    userPhotoUrl = "https://wallpapers.com/images/hd/meme-profile-picture-2rhxt0ddudotto63.jpg",
                    petPhotoUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/a/ae/Sphynx_cat_in_orange_sweater_2014_%28cropped_2023%29.jpg/1200px-Sphynx_cat_in_orange_sweater_2014_%28cropped_2023%29.jpg",
                    petBreed = "Sphynx Cat",
                    petName = "Abe",
                    onAdopt = {}
                )
                Divider(Modifier.fillMaxWidth())
            }

            if (state.isScrollInProgress) {
                movingScrollValue = state.value

                isScrollingDown = movingScrollValue < currentScrollValue

                DisposableEffect(Unit) {
                    onDispose {
                        currentScrollValue = state.value
                    }
                }
            }


            if(isScrollingDown){

                FloatingActionButton(
                    onClick = {},
                    containerColor = MaterialTheme.colorScheme.secondary,
                    elevation = FloatingActionButtonDefaults.elevation(8.dp),
                    modifier = Modifier.align(Alignment.BottomEnd).padding(24.dp)
                ) {
                    Icon(Icons.Default.Add, contentDescription = null)

                }
            }


        }


    }
}

@Composable
fun AdoptTopInput() {

    var text by remember { mutableStateOf("") }

    Row(
        modifier = Modifier.padding(horizontal = 24.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        OutlinedButton(
            onClick = { /*TODO*/ },
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = MaterialTheme.colorScheme.onSurface,
            ),
            modifier = Modifier.weight(1f),
            border = BorderStroke(
                0.75.dp,
                MaterialTheme.colorScheme.onSurface,
            )

        ) {

            Text("Whats on your mind?")
        }

        IconButton(onClick = {}, Modifier.size(36.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.image_icon),
                contentDescription = null,
                Modifier.padding(2.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AdoptScreenPreview() {
    PatypetTheme {
        AdoptScreen()
    }
}