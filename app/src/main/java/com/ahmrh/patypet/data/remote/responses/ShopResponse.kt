package com.ahmrh.patypet.data.remote.responses

import com.google.gson.annotations.SerializedName

data class ShopResponse(

	@field:SerializedName("ShopResponse")
	val shopResponse: List<ShopResponseItem?>? = null
)

data class ShopResponseItem(

	@field:SerializedName("product_url")
	val productUrl: String? = null,

	@field:SerializedName("product_id")
	val productId: Int? = null,

	@field:SerializedName("product_price")
	val productPrice: Any? = null,

	@field:SerializedName("product_name")
	val productName: String? = null,

	@field:SerializedName("product_img")
	val productImg: String? = null
)
