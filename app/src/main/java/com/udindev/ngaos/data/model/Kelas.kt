package com.udindev.ngaos.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Kelas (
        var id: String? = "",
        var namaKelas : String? ="",
        var pengajar : String? = "",
        var waktuMulai : String? = "",
        var waktuSelesai : String? = "",
        var deskripsi : String? = "",
        var photo: String? = ""
        ): Parcelable