package com.udindev.ngaos.api

class ApiHelper(private val apiService: ApiService) {

    suspend fun getTimeFromFatimah (latitude : String,longitude : String,date : String)
    = apiService.getTimeFromFatimah(latitude,longitude, date)

    suspend fun  getAllKelas() = apiService.getAllKelas()

    suspend fun getKelasDiikuti(id : String) = apiService.getKelasDiikuti(id)

    suspend fun pembayaran( idUser: String,
                           namaPembayaran: String,
                           status: String,
                          totalPembayaran: Int,
                           jenisPembayaran: String,
                           buktiPembayaran: String) =
        apiService.uploadPembayaran(idUser, namaPembayaran, status, totalPembayaran, jenisPembayaran, buktiPembayaran)
}