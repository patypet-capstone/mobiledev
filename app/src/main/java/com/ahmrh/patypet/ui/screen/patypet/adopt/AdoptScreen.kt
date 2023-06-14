package com.ahmrh.patypet.ui.screen.patypet.adopt

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ahmrh.patypet.ui.components.card.AdoptCard
import com.ahmrh.patypet.ui.components.card.CaretakeCard
import com.ahmrh.patypet.ui.theme.PatypetTheme

@Composable
fun AdoptScreen() {


    LazyColumn(
        Modifier.fillMaxSize()
            .padding(
                horizontal = 12.dp,
                vertical = 12.dp
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {

        items(5){
            AdoptCard()
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