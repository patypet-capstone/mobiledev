package com.ahmrh.patypet.ui.screen.patypet.vet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ahmrh.patypet.ui.components.card.VetCard
import com.ahmrh.patypet.ui.theme.PatypetTheme

@Composable
fun VetScreen(){
    Surface{

        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(
                    horizontal = 24.dp,
                    vertical = 12.dp
                ),
        ){

            Row(){

                Text("Found 4 ")
                Text("Vet", fontWeight = FontWeight.Bold)
                Text(" nearby")
            }
            Spacer(Modifier.size(12.dp))

            Column(
                Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                VetCard(
                    photoUrl = "https://www.vetmed.ucdavis.edu/sites/g/files/dgvnsk491/files/styles/sf_landscape_16x9/public/media/images/20191014sean_lucy033.jpg?h=033c0e4d&itok=7W-HHrLk",
                    name = "John Doe",
                    location = "Jakarta Barat"
                )
                VetCard(
                    photoUrl = "https://www.bankrate.com/2022/02/28132619/How-to-pay-for-vet-school.jpg",
                    name = "Susan",
                    location = "Jakarta Barat"
                )
                VetCard(
                    photoUrl = "https://veterinary.rossu.edu/sites/g/files/krcnkv416/files/styles/atge_default_md/public/2021-08/blog-veterinary-vet-school-requirements-to-know_herom.jpg?itok=ppqCvyru",
                    name = "Jennifer",
                    location = "Jakarta Tengah"
                )
                VetCard(
                    photoUrl = "https://bmorehumane.org/wp-content/uploads/2020/06/wellness-vet-with-lab-1024x768.jpg",
                    name = "Collier",
                    location = "Jakarta Tengah"
                )
            }
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