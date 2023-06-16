package com.ahmrh.patypet.domain.use_case.pet

import javax.inject.Inject

data class PetUseCases @Inject constructor(
    val predict: PredictUseCase,
    val getPet : GetPetUseCase,
    val fetchArticle : FetchArticleUseCase,
    val getShopProduct: GetShopProductUseCase,
    val getPetById: GetPetByIdUseCase,
    val savePet: SavePetUseCase

)
