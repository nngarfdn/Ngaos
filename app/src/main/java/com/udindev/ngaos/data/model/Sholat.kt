package com.udindev.ngaos.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Sholat(
    var lokasi : String? = "",
    var subuh : String? = "",
    var dzuhur : String? = "",
    var ashar : String? = "",
    var magrib : String? = "",
    var isya : String? = ""
) : Parcelable