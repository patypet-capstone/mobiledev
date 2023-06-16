package com.ahmrh.patypet.data.remote.responses

import com.google.gson.annotations.SerializedName

data class ChangeNameResponse(

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)
