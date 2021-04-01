package com.udindev.ngaos.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Sholat(
    var lokasi : String? = "",
    var subuh : String? = "",
    var dzuhur : String? = "",
    var ashar : String? = "",
    var magrib : String? = "",
    var isya : String? = ""
) : Parcelable