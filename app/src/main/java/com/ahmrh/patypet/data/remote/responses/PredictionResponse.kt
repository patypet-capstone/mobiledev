package com.ahmrh.patypet.data.remote.responses

import com.google.gson.annotations.SerializedName

data class PredictionResponse(

	@field:SerializedName("breed_data")
	val breedData: BreedData? = null,

	@field:SerializedName("image_url")
	val imageUrl: String? = null,

	@field:SerializedName("confidence")
	val confidence: Float? = null,

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
	val colours: String? = null,

	@field:SerializedName("Body_Features")
	val bodyFeatures: String? = null,

	@field:SerializedName("Lifespan")
	val lifespan: String? = null,

	@field:SerializedName("Health_Conditions")
	val healthConditions: String? = null,

	@field:SerializedName("Weight")
	val weight: String? = null
)
