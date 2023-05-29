package com.ahmrh.patypet.data.remote.responses

import com.google.gson.annotations.SerializedName

data class UserResponse(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("email")
	val email: String? = null
)
