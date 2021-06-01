package com.udindev.ngaos.api

import com.udindev.ngaos.data.response.ResponsePrayerTime
import com.udindev.ngaos.data.response.kelas.KelasResponse
import retrofit2.http.GET
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

}