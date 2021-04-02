package com.nanang.retrocoro.ui.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.udindev.ngaos.data.repository.SholatRepository
import com.udindev.ngaos.utils.Resource

import kotlinx.coroutines.Dispatchers

class SholatViewModel(private val mainRepository: SholatRepository): ViewModel() {
    fun  getTimeFromFatimah (latitude : String,longitude : String,date : String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = mainRepository.getTimeFromFatimah(latitude,longitude, date)))
        }catch (e : Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }
}