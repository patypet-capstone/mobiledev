package com.ahmrh.patypet.data.remote.responses

import com.google.gson.annotations.SerializedName

data class RegisterResponse(

	@field:SerializedName("message")
	val message: String? = null
)
