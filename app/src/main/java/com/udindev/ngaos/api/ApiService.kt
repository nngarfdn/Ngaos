package com.udindev.ngaos.api

import com.udindev.ngaos.data.response.ResponsePrayerTime
import com.udindev.ngaos.data.response.diikuti.ResponseDiikuti
import com.udindev.ngaos.data.response.kelas.KelasResponse
import com.udindev.ngaos.data.response.pembayaran.ResponsePostPembayaran
import retrofit2.http.*

interface ApiService {

    @GET("day.json")
    suspend fun getTimeFromFatimah(
            @Query("latitude") latitude : String,
            @Query("longitude") longitude : String,
            @Query("date") date : String
    ): ResponsePrayerTime

    @GET("kelas")
    suspend fun getAllKelas(): KelasResponse

    @GET("diikuti/{id}")
    suspend fun getKelasDiikuti(
        @Path("id") id : String
    ): ResponseDiikuti

    @FormUrlEncoded
    @POST("pembayaran")
    suspend fun uploadPembayaran(
        @Field("idUser") idUser: String,
        @Field("namaPembayaran") namaPembayaran: String,
        @Field("status") status: String,
        @Field("totalPembayaran") totalPembayaran: Int,
        @Field("jenisPembayaran") jenisPembayaran: String,
        @Field("buktiPembayaran") buktiPembayaran: String
    ) : ResponsePostPembayaran

}