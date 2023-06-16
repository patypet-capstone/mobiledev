package com.ahmrh.patypet.ui.screen.patypet.shop

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ahmrh.patypet.R
import com.ahmrh.patypet.common.UiState
import com.ahmrh.patypet.data.remote.responses.ShopResponseItem
import com.ahmrh.patypet.ui.components.card.ProductCard
import com.ahmrh.patypet.ui.components.dialog.CustomDialog
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShopScreen(

    onSearch: (query: String) -> Unit = {},
    onJenisChange: (jenis: String) -> Unit = {},
    onProductChange: (product: String) -> Unit = {},
    productUiState: StateFlow<UiState<List<ShopResponseItem>>>,
    onGetProduct: () -> Unit = {},
) {
    Surface (
        modifier = Modifier.fillMaxSize()
            ){
        var active by rememberSaveable {
            mutableStateOf(
                false
            )
        }
        var query by remember { mutableStateOf("") }
        val snackbarHostState =
            remember { SnackbarHostState() }
        val scope = rememberCoroutineScope()

        Scaffold(

            snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
        ){

            Column(
                modifier = Modifier
                    .padding(it)
                    .padding(horizontal = 24.dp, vertical = 12.dp)
            ) {

                SearchBar(
                    query = query,
                    onQueryChange = {
                        query = it
                        onSearch(query)
                    },
                    onSearch = {
                        query = it

                        onSearch(query)
                    },
                    active = active,
                    onActiveChange = { active = false },
                    placeholder = { Text("Search caretake") },
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


                Row(
                    horizontalArrangement = Arrangement.spacedBy(
                        8.dp
                    ),
//                modifier = Modifier
//                    .horizontalScroll(rememberScrollState())
                ) {
                    var grooming by remember {
                        mutableStateOf(
                            false
                        )
                    }
                    var food by remember { mutableStateOf(false) }

                    FilterChip(
                        selected = false,
                        onClick = {
                            scope.launch {
                                snackbarHostState.showSnackbar(
                                    message = "Feature still under development",
                                    duration = SnackbarDuration.Short
                                )
                            }
                        },
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
                    productUiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
                        if (uiState is UiState.Loading) {
                            DisabledShopChip(grooming, food)

                        } else{


                            FilterChip(
                                selected = grooming,
                                onClick = {
                                    grooming = !grooming
                                    var value = "all"
                                    if (!grooming && food) value =
                                        "food_product"
                                    if (grooming && !food) value =
                                        "groom_product"

                                    onProductChange(value)
                                },
                                label = { Text("Grooming") },
                                colors = FilterChipDefaults.filterChipColors(
                                    labelColor = MaterialTheme.colorScheme.secondary,
                                    iconColor = MaterialTheme.colorScheme.secondary,
                                )
                            )


                            FilterChip(
                                selected = food,
                                onClick = {
                                    food = !food
                                    var value = "all"
                                    if (!grooming && food) value =
                                        "food_product"
                                    if (grooming && !food) value =
                                        "groom_product"

                                    onProductChange(value)

                                },

                                label = { Text("Food") },
                                colors = FilterChipDefaults.filterChipColors(
                                    labelColor = MaterialTheme.colorScheme.secondary,
                                    iconColor = MaterialTheme.colorScheme.secondary,
                                )
                            )


//                var other by remember { mutableStateOf(false) }
//                FilterChip(
//                    selected = other,
//                    onClick = { other = !other },
//                    label = { Text("Other") },
//                )

                        }
                    }

                }

                val uriHandler = LocalUriHandler.current

                Spacer(Modifier.height(8.dp))

                productUiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
                    when (uiState) {
                        is UiState.Idle -> {
                            Box(modifier = Modifier.fillMaxSize()) {

                                CircularProgressIndicator(
                                    Modifier.align(
                                        Alignment.Center
                                    )
                                )
                            }
                            // do nothing
                        }

                        is UiState.Loading -> {
                            Box(modifier = Modifier.fillMaxSize()) {

                                CircularProgressIndicator(
                                    Modifier.align(
                                        Alignment.Center
                                    )
                                )
                            }
                        }

                        is UiState.Success -> {

                            val shopProducts =
                                (productUiState.value as UiState.Success<List<ShopResponseItem>>).data

                            LazyVerticalGrid(
                                columns = GridCells.Adaptive(
                                    minSize = 128.dp
                                ),
                                verticalArrangement = Arrangement.spacedBy(
                                    16.dp
                                ),
                                horizontalArrangement = Arrangement.spacedBy(
                                    16.dp
                                ),
                            ) {
                                items(shopProducts) { product ->
                                    ProductCard(
                                        name = product.productName.toString(),
                                        photoUrl = product.productImg,
                                        price = product.productPrice as Double,
                                        onCardClicked = {
                                            uriHandler.openUri(
                                                product.productUrl
                                                    ?: ""
                                            )
                                        }


                                    )
                                }

                            }
                        }

                        is UiState.Error -> {
                            Box(modifier = Modifier.fillMaxSize()) {
                                CustomDialog(
                                    title = "Connection Problem",
                                    body = "There seems to be a problem with your connection, please try again.",
                                    onDismiss = onGetProduct
                                )
                            }

                        }
                    }
                }
            }
        }


    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DisabledShopChip(grooming: Boolean, food: Boolean) {



    FilterChip(
        selected = grooming,
        onClick = {},
        label = { Text("Grooming") },
        colors = FilterChipDefaults.filterChipColors(
            labelColor = MaterialTheme.colorScheme.secondary,
            iconColor = MaterialTheme.colorScheme.secondary,
        ),
        enabled = false
    )


    FilterChip(
        selected = food,
        onClick = {},

        label = { Text("Food") },
        colors = FilterChipDefaults.filterChipColors(
            labelColor = MaterialTheme.colorScheme.secondary,
            iconColor = MaterialTheme.colorScheme.secondary,
        ),
        enabled = false
    )
}

