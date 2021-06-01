package com.udindev.ngaos.data.repository

import com.udindev.ngaos.api.ApiHelper

class KelasRepository(private val apiHelper : ApiHelper) {

    suspend fun getAllKelas() = apiHelper.getAllKelas()
}