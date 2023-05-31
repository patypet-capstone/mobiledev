package com.ahmrh.patypet.data.remote.responses

import com.google.gson.annotations.SerializedName

data class RemoteResponse(

    @field:SerializedName("success")
    val success: Boolean,

    @field:SerializedName("message")
    val message: String,
)
