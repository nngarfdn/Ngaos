package com.nanang.retrocoro.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nanang.retrocoro.ui.main.viewmodel.SholatViewModel
import com.udindev.ngaos.api.ApiHelper
import com.udindev.ngaos.data.repository.KelasRepository
import com.udindev.ngaos.data.repository.SholatRepository
import com.udindev.ngaos.ui.dashboard.main.view.KelasViewModel

class ViewModelFactory(private val apiHelper: ApiHelper) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SholatViewModel::class.java)){
            return SholatViewModel(SholatRepository(apiHelper)) as T
        }

        if (modelClass.isAssignableFrom(KelasViewModel::class.java)){
            return KelasViewModel(KelasRepository(apiHelper)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}