package com.udindev.ngaos.data.response.pembayaran


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("buktiPembayaran")
    val buktiPembayaran: String? = null,
    @SerializedName("created_at")
    val createdAt: String? = "null",
    @SerializedName("id")
    val id: Int? = 0,
    @SerializedName("idUser")
    val idUser: String? = "null",
    @SerializedName("jenisPembayaran")
    val jenisPembayaran: String? = "null",
    @SerializedName("namaPembayaran")
    val namaPembayaran: String? = "null",
    @SerializedName("status")
    val status: String? = "null",
    @SerializedName("totalPembayaran")
    val totalPembayaran: Int? = 0,
    @SerializedName("updated_at")
    val updatedAt: String? = "null"
)