package com.udindev.ngaos.data.repository

import com.udindev.ngaos.api.ApiHelper

class SholatRepository(private val apiHelper : ApiHelper) {
    suspend fun getTimeFromFatimah (latitude : String,longitude : String,date : String)
    = apiHelper.getTimeFromFatimah(latitude, longitude, date)
}