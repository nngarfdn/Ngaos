package com.udindev.ngaos.api

import com.udindev.ngaos.data.response.ResponsePrayerTime
import com.udindev.ngaos.data.response.diikuti.ResponseDiikuti
import com.udindev.ngaos.data.response.kelas.KelasResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

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

}