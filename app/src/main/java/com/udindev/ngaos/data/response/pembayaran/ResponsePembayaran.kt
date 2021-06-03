package com.udindev.ngaos.data.response.pembayaran


import com.google.gson.annotations.SerializedName

data class ResponsePembayaran(
    @SerializedName("data")
    val `data`: List<Data>? = null,
    @SerializedName("success")
    val success: String? = null
)