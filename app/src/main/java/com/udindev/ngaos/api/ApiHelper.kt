package com.udindev.ngaos.api

class ApiHelper(private val apiService: ApiService) {
    suspend fun getTimeFromFatimah (latitude : String,longitude : String,date : String)
    = apiService.getTimeFromFatimah(latitude,longitude, date)
}