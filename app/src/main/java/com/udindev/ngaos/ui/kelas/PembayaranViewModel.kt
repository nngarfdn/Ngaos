package com.udindev.ngaos.ui.kelas

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import com.udindev.ngaos.callback.OnImageUploadCallback
import com.udindev.ngaos.data.repository.PembayaranRepository

class PembayaranViewModel(private val repository: PembayaranRepository) : ViewModel(){

    fun uploadImage(context: Context?, portfolioId: String?, uri: Uri?, fileName: String?, callback: OnImageUploadCallback?) {
        if (callback != null) {
            repository.uploadImage(context, portfolioId!!, uri, fileName!!, callback)
        }
    }

}