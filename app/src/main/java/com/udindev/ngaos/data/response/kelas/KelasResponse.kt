package com.udindev.ngaos.data.response.kelas


import com.google.gson.annotations.SerializedName

data class KelasResponse(
    @SerializedName("data")
    val `data`: List<Data>? = null,
    @SerializedName("success")
    val success: String? = null
)