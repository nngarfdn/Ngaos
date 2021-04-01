package com.udindev.ngaos.api

import com.nanang.berberita.data.api.ApiService

class ApiHelper(private val apiService: ApiService) {
    suspend fun getTimeFromFatimah (latitude : String,longitude : String,date : String)
    = apiService.getTimeFromFatimah(latitude,longitude, date)
}