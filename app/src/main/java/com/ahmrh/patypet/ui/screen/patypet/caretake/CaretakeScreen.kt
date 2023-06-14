package com.ahmrh.patypet.ui.screen.patypet.caretake

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ahmrh.patypet.R
import com.ahmrh.patypet.ui.components.card.CaretakeCard
import com.ahmrh.patypet.ui.theme.PatypetTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CaretakeScreen(){
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
            CaretakeSearch()
            CaretakeFilter()

            Spacer(Modifier.size(8.dp))

            Row(){

                Text("Found 3 ")
                Text("Caretake", fontWeight = FontWeight.Bold)
                Text(" nearby")
            }

            Spacer(Modifier.size(12.dp))

            Column(
                Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                CaretakeCard(
                    label = listOf("Dog", "Cat"),
                    name = "Autumn Pet Caretake",
                    location = "Jakarta Utara",
                    price = "100.000/hari",
                    photoUrl = "https://mommiesdaily.com/wp-content/uploads/2021/12/Pet-Hotel-728x410.jpg"
                )
                CaretakeCard(
                    label = listOf("Dog"),
                    name = "Woof Avenue",
                    location = "Jakarta Timur",
                    price = "80.000/hari",
                    photoUrl = "https://mommiesdaily.com/wp-content/uploads/2021/12/Woof-Avenue-328x410.jpg"
                )
                CaretakeCard(
                    label = listOf("Dog"),
                    name = "Chilldog House",
                    location = "Jakarta Timur",
                    price = "50.000/hari",
                    photoUrl = "https://mommiesdaily.com/wp-content/uploads/2021/12/Chilli-Dog-House-Mommies-Daily-547x410.jpeg"
                )
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CaretakeSearch(){

    var active by rememberSaveable {
        mutableStateOf(
            false
        )
    }
    var query by remember { mutableStateOf("") }
    SearchBar(
        query = query,
        onQueryChange = {
            query = it
        },
        onSearch = {
            query = it

        },
        active = active,
        onActiveChange = { active = false },
        placeholder = { Text("Search caretaker") },
        leadingIcon = {
            Icon(
                Icons.Default.Search,
                contentDescription = null
            )
        },
        trailingIcon = {},
        modifier = Modifier
            .fillMaxWidth(1f),


        ) {}



    Spacer(Modifier.height(8.dp))
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CaretakeFilter(){
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ){

        var dog by remember {
            mutableStateOf(
                false
            )
        }
        var cat by remember { mutableStateOf(false) }

        FilterChip(
            selected = false,
            onClick = { },
            label = { Text("Filter") },
            leadingIcon = {

                Icon(
                    painter = painterResource(id = R.drawable.filter_icon),
                    contentDescription = "Localized Description",
                    modifier = Modifier.size(
                        FilterChipDefaults.IconSize
                    )
                )
            },
            colors = FilterChipDefaults.filterChipColors(
                labelColor = MaterialTheme.colorScheme.secondary,
                iconColor = MaterialTheme.colorScheme.secondary,
            )

        )
        FilterChip(
            selected = dog,
            onClick = {
                dog = !dog
                var value = "all"
                if (!dog && cat) value =
                    "food_product"
                if (dog && !cat) value =
                    "groom_product"

            },
            label = { Text("Dog") },
            colors = FilterChipDefaults.filterChipColors(
                labelColor = MaterialTheme.colorScheme.secondary,
                iconColor = MaterialTheme.colorScheme.secondary,
            )
        )


        FilterChip(
            selected = cat,
            onClick = {
                cat = !cat
                var value = "all"
                if (!dog && cat) value =
                    "food_product"
                if (dog && !cat) value =
                    "groom_product"


            },

            label = { Text("Cat") },
            colors = FilterChipDefaults.filterChipColors(
                labelColor = MaterialTheme.colorScheme.secondary,
                iconColor = MaterialTheme.colorScheme.secondary,
            )
        )
    }
}

@Preview(showBackground= true)
@Composable
fun CaretakeScreenPreview(){
    PatypetTheme{
        CaretakeScreen()
    }
}