package com.udindev.ngaos.data.repository

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.udindev.ngaos.callback.OnImageUploadCallback
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException


class PembayaranRepository {

    companion object{
        private const val TAG = "PembayaranRepository"
        const val FOLDER_BUKU = "buktipembayaran"
    }

    private val storage = FirebaseStorage.getInstance()

    fun uploadImage(context: Context?, portfolioId: String, uri: Uri?, fileName: String, callback: OnImageUploadCallback) { // Investor
        var image: ByteArray? = context?.let { convertUriToByteArray(it, uri) }
        image = getCompressedByteArray(image, true)
        val reference: StorageReference = storage.reference.child("$FOLDER_BUKU/$portfolioId/$fileName")
        val uploadTask = reference.putBytes(image!!)
        uploadTask.addOnSuccessListener {
            reference.downloadUrl.addOnSuccessListener { uri1: Uri ->
                callback.onSuccess(uri1.toString())
                Log.d(TAG, "Image was uploaded")
            }
        }.addOnFailureListener { e: Exception? -> Log.w(TAG, "Error uploading image", e) }
    }

    fun convertUriToByteArray(context: Context, uri: Uri?): ByteArray? {
        var stream: ByteArrayOutputStream? = null
        try {
            // Convert uri to bitmap
            val bitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, uri)

            // Convert bitmap to byte[]
            stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            try {
                stream.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        assert(stream != null)
        return stream?.toByteArray()
    }

    fun getCompressedByteArray(image: ByteArray?, isResized: Boolean): ByteArray? {
        var stream: ByteArrayOutputStream? = null
        try {
            // Convert byte[] to bitmap
            var bitmap = BitmapFactory.decodeStream(ByteArrayInputStream(image))
            if (isResized) {
                // Change bitmap size
                if (bitmap.width > 1024) {
                    bitmap = Bitmap.createScaledBitmap(
                        bitmap, 1024,
                        (bitmap.height * (1024.0 / bitmap.width)).toInt(), true
                    )
                }
            }

            // Compress bitmap and get byte[]
            stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream)
            try {
                stream.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        assert(stream != null)
        return stream!!.toByteArray()
    }


}