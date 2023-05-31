package com.ahmrh.patypet.data.remote.responses

import com.google.gson.annotations.SerializedName

data class PredictionResponse(

	@field:SerializedName("image_url")
	val imageUrl: String? = null,

	@field:SerializedName("confidence")
	val confidence: Any? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("predicted_label")
	val predictedLabel: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)
