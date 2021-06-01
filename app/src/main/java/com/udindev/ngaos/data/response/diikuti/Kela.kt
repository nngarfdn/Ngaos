package com.udindev.ngaos.data.response.diikuti


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Kela(
    @SerializedName("biaya")
    val biaya: Int? = null,
    @SerializedName("created_at")
    val createdAt: String? = null,
    @SerializedName("deskripsiKelas")
    val deskripsiKelas: String? = null,
    @SerializedName("fotoKelas")
    val fotoKelas: String? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("idPengajar")
    val idPengajar: Int? = null,
    @SerializedName("idUser")
    val idUser: String? = null,
    @SerializedName("isTersedia")
    val isTersedia: Int? = null,
    @SerializedName("kuotaKelas")
    val kuotaKelas: Int? = null,
    @SerializedName("linkGrupWa")
    val linkGrupWa: String? = null,
    @SerializedName("namaKelas")
    val namaKelas: String? = null,
    @SerializedName("updated_at")
    val updatedAt: String? = null,
    @SerializedName("waktuMulai")
    val waktuMulai: String? = null,
    @SerializedName("waktuSelesai")
    val waktuSelesai: String? = null
) :Parcelable