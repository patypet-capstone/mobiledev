package com.ahmrh.patypet.ui.screen.patypet.vet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ahmrh.patypet.ui.components.card.VetCard
import com.ahmrh.patypet.ui.theme.PatypetTheme

@Composable
fun VetScreen(){


    LazyColumn(
        Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        items(5){
            VetCard()
        }
    }
}

@Preview(showBackground= true)
@Composable
fun VetScreenPreview(){
    PatypetTheme{
        VetScreen()
    }
}