package com.udindev.ngaos.data.response.diikuti


import com.google.gson.annotations.SerializedName

data class ResponseDiikuti(
    @SerializedName("data")
    val `data`: Data? = null,
    @SerializedName("success")
    val success: String? = null
)