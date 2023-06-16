package com.ahmrh.patypet.data.remote.responses

import com.google.gson.annotations.SerializedName

data class PetByIdResponse(

	@field:SerializedName("breed_data")
	val breedData: BreedData? = null,

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("status")
	val status: String? = null
)



data class Data(

	@field:SerializedName("image_url")
	val imageUrl: String? = null,

	@field:SerializedName("confidence")
	val confidence: Any? = null,

	@field:SerializedName("name")
	val name: Any? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("predicted_label")
	val predictedLabel: String? = null,

	@field:SerializedName("email")
	val email: String? = null
)
