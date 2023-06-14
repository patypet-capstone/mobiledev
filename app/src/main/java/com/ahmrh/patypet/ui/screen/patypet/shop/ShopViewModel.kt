package com.ahmrh.patypet.ui.screen.patypet.shop

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmrh.patypet.common.Resource
import com.ahmrh.patypet.common.UiState
import com.ahmrh.patypet.data.remote.responses.ShopResponseItem
import com.ahmrh.patypet.domain.use_case.pet.PetUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ShopViewModel @Inject constructor(
    private val petUseCases: PetUseCases,
) : ViewModel() {

    private val _productUiState =
        MutableStateFlow<UiState<List<ShopResponseItem>>>(
            UiState.Idle
        )
    val productUiState: StateFlow<UiState<List<ShopResponseItem>>> =
        _productUiState

    private var products = mutableListOf<ShopResponseItem>()

    private val _query = mutableStateOf("")
    val query: State<String> get() = _query


    private val _jenis = mutableStateOf("")

    private val _product = mutableStateOf("")

    init {
        getShopProduct()
    }

    fun setJenis(jenis: String) {
        _jenis.value = jenis
        getShopProduct()
    }

    fun setProduct(product: String) {
        _product.value = product
        getShopProduct()
    }


    fun getShopProduct() {

        _productUiState.value = UiState.Loading

        val product =
            if (_product.value == "") null else _product.value
        val jenis =
            if (_jenis.value == "") null else _jenis.value

        petUseCases.getShopProduct(product, jenis)
            .onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        products =
                            result.data!!.toMutableList()
                        _productUiState.value =
                            UiState.Success(products.filter {
                                it.productName!!.contains(
                                    _query.value,
                                    ignoreCase = true
                                )
                            })
                    }

                    is Resource.Error -> {
                        _productUiState.value =
                            UiState.Error(
                                result.message
                                    ?: "Unexpected Error Occured"
                            )
                    }

                    is Resource.Loading -> {
                        _productUiState.value =
                            UiState.Loading
                    }
                }
            }.launchIn(viewModelScope)

    }

    fun searchProduct(query: String) {

        _query.value = query
        getShopProduct()

        Log.d(
            "Search Product",
            "does it even run? query: $query"
        )
    }


}