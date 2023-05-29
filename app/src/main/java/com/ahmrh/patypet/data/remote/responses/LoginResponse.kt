package com.ahmrh.patypet.data.remote.responses

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("token")
	val token: String? = null
)
