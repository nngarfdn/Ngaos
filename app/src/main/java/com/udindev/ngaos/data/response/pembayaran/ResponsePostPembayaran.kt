package com.udindev.ngaos.data.response.pembayaran


import com.google.gson.annotations.SerializedName

data class ResponsePostPembayaran(
    @SerializedName("message")
    val message: String? = null,
    @SerializedName("success")
    val success: String? = null
)