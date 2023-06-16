package com.ahmrh.patypet.data.remote.responses

import com.google.gson.annotations.SerializedName

data class SaveResponse(

	@field:SerializedName("prediction_id")
	val predictionId: Int? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)
