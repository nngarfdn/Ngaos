package com.udindev.ngaos.ui.dashboard.main.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.udindev.ngaos.data.repository.KelasRepository
import com.udindev.ngaos.utils.Resource
import kotlinx.coroutines.Dispatchers

class KelasViewModel(private val mainRepository: KelasRepository): ViewModel() {
    fun  getAllKelas() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = mainRepository.getAllKelas()))
        }catch (e : Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }
}