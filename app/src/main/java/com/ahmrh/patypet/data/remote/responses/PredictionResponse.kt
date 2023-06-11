package com.ahmrh.patypet.data.remote.responses

import com.google.gson.annotations.SerializedName

data class PredictionResponse(

	@field:SerializedName("breed_data")
	val breedData: BreedData? = null,

	@field:SerializedName("image_url")
	val imageUrl: String? = null,

	@field:SerializedName("confidence")
	val confidence: Any? = null,

	@field:SerializedName("shop_data")
	val shopData: List<ShopDataItem?>? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("predicted_label")
	val predictedLabel: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class BreedData(

	@field:SerializedName("Breed")
	val breed: String? = null,

	@field:SerializedName("Description")
	val description: String? = null,

	@field:SerializedName("Grooming")
	val grooming: String? = null,

	@field:SerializedName("Colours")
	val colours: Colours? = null,

	@field:SerializedName("Personality")
	val personality: String? = null,

	@field:SerializedName("Body_Features")
	val bodyFeatures: BodyFeatures? = null,

	@field:SerializedName("Lifespan")
	val lifespan: String? = null,

	@field:SerializedName("Weight")
	val weight: String? = null
)

data class BodyFeatures(

	@field:SerializedName("ears")
	val ears: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("head_shape")
	val headShape: String? = null,

	@field:SerializedName("body")
	val body: String? = null
)

data class GroomDataItem(

	@field:SerializedName("product_url")
	val productUrl: String? = null,

	@field:SerializedName("product_price")
	val productPrice: Any? = null,

	@field:SerializedName("product_name")
	val productName: String? = null,

	@field:SerializedName("product_img")
	val productImg: String? = null
)

data class Colours(

	@field:SerializedName("color1")
	val color1: String? = null,

	@field:SerializedName("color2")
	val color2: String? = null,

	@field:SerializedName("color3")
	val color3: String? = null
)

data class FoodDataItem(

	@field:SerializedName("product_url")
	val productUrl: String? = null,

	@field:SerializedName("product_price")
	val productPrice: Any? = null,

	@field:SerializedName("product_name")
	val productName: String? = null,

	@field:SerializedName("product_img")
	val productImg: String? = null
)

data class ShopDataItem(

	@field:SerializedName("groom_data")
	val groomData: List<GroomDataItem?>? = null,

	@field:SerializedName("food_data")
	val foodData: List<FoodDataItem?>? = null
)
