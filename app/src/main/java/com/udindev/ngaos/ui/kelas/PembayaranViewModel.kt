package com.udindev.ngaos.ui.kelas

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.udindev.ngaos.callback.OnImageUploadCallback
import com.udindev.ngaos.data.repository.PembayaranRepository
import com.udindev.ngaos.utils.Resource
import kotlinx.coroutines.Dispatchers

class PembayaranViewModel(private val repository: PembayaranRepository) : ViewModel(){

    fun uploadImage(context: Context?, portfolioId: String?, uri: Uri?, fileName: String?, callback: OnImageUploadCallback?) {
        if (callback != null) {
            repository.uploadImage(context, portfolioId!!, uri, fileName!!, callback)
        }
    }

    fun  uploadPembayaran(idUser: String,
                          namaPembayaran: String,
                          status: String,
                          totalPembayaran: Int,
                          jenisPembayaran: String,
                          buktiPembayaran: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = repository.uploadPembayaran(idUser, namaPembayaran, status, totalPembayaran, jenisPembayaran, buktiPembayaran)))
        }catch (e : Exception) {
            emit(Resource.error(data = null, message = e.message ?: "Error Occurred!"))
        }
    }

}