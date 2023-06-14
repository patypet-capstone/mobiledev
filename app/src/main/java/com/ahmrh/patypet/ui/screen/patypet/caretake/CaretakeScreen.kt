package com.ahmrh.patypet.ui.screen.patypet.caretake

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ahmrh.patypet.ui.components.card.CaretakeCard
import com.ahmrh.patypet.ui.components.card.VetCard
import com.ahmrh.patypet.ui.theme.PatypetTheme

@Composable
fun CaretakeScreen(){
    Column(
        Modifier.fillMaxSize()
            .padding(
                horizontal = 24.dp,
                vertical = 12.dp
            ),
    ){

        Text("Found 5 Caretake Location nearby")

        Spacer(Modifier.size(12.dp))

        LazyColumn(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            items(5){
                CaretakeCard()
            }
        }
    }

}

@Preview(showBackground= true)
@Composable
fun CaretakeScreenPreview(){
    PatypetTheme{
        CaretakeScreen()
    }
}