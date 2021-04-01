package com.nanang.berberita.data.api

import com.udindev.ngaos.model.ResponsePrayerTime
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("day.json")
    suspend fun getTimeFromFatimah(
            @Query("latitude") latitude : String,
            @Query("longitude") longitude : String,
            @Query("date") date : String
    ): ResponsePrayerTime

}