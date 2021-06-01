package com.udindev.ngaos.data.response.diikuti


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("kelas")
    val kelas: List<Kela>? = null,
    @SerializedName("user")
    val user: List<User>? = null
)