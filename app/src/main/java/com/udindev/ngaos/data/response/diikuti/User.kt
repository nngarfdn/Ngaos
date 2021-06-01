package com.udindev.ngaos.data.response.diikuti


import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("created_at")
    val createdAt: String? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("idUser")
    val idUser: String? = null,
    @SerializedName("updated_at")
    val updatedAt: String? = null
)