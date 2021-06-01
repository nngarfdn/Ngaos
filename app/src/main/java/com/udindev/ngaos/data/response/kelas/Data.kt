package com.udindev.ngaos.data.response.kelas

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Data(
    var biaya: Int? = null,
    var created_at: String? = null,
    var deskripsiKelas: String? = null,
    var fotoKelas: String? = null,
    var idUser : String? = null,
    var id: Int? = null,
    var idPengajar: Int? = null,
    var isTersedia: Int? = null,
    var kuotaKelas: Int? = null,
    var linkGrupWa: String? = null,
    var namaKelas: String? = null,
    var updated_at: String? = null,
    var waktuMulai: String? = null,
    var waktuSelesai: String? = null
) : Parcelable