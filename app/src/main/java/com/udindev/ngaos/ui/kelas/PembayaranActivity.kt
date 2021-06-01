package com.udindev.ngaos.ui.kelas

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.udindev.ngaos.R
import com.udindev.ngaos.databinding.ActivityPembayaranBinding
import com.udindev.ngaos.utils.AppUtils.loadImageFromUrl
import com.udindev.ngaos.utils.costumview.LoadingDialog

class PembayaranActivity : AppCompatActivity() {

    companion object{
        private const val RC_PICK_IMAGE = 100
    }

    private var uriImage: Uri? = null
    private var firebaseAuth: FirebaseAuth? = null
    private var firebaseUser: FirebaseUser? = null
    private var loadingDialog: LoadingDialog? = null
    private lateinit var binding : ActivityPembayaranBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPembayaranBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        firebaseAuth = FirebaseAuth.getInstance()
        firebaseUser = firebaseAuth!!.currentUser
        loadingDialog = LoadingDialog(this, false)

        binding.btnPilihGambar.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(Intent.createChooser(intent, "Unggah foto"), RC_PICK_IMAGE)
        }

        binding.txtPetunjukBayar.text =
            "Diisi petunjuk penbayaran Gopay \n" +
                    "1. langkah pertama \n" +
                    "2. langkah selanjutnya \n" +
                    "3. langkah dst"

        setupPetuntukPembayaran()
    }

    @SuppressLint("SetTextI18n")
    private fun setupPetuntukPembayaran() {
        binding.radioGroup1.setOnCheckedChangeListener { _, id ->
            if (id == R.id.radioSubuh) binding.txtPetunjukBayar.text =
                "Diisi petunjuk penbayaran Gopay \n" +
                        "1. langkah pertama \n" +
                        "2. langkah selanjutnya" +
                        "3. langkah dst"
            if (id == R.id.radioDzuhur) binding.txtPetunjukBayar.text =
                "Diisi petunjuk penbayaran OVO \n" +
                        "1. langkah pertama \n" +
                        "2. langkah selanjutnya\n" +
                        "3. langkah dst"
            if (id == R.id.radioAshar) binding.txtPetunjukBayar.text =
                "Diisi petunjuk penbayaran Mandiri \n" +
                        "1. langkah pertama \n" +
                        "2. langkah selanjutnya\n" +
                        "3. langkah dst"
            if (id == R.id.radioBCA) binding.txtPetunjukBayar.text =
                "Diisi petunjuk penbayaran BCA \n" +
                        "1. langkah pertama \n" +
                        "2. langkah selanjutnya\n" +
                        "3. langkah dst"
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_PICK_IMAGE) {
            if (resultCode == RESULT_OK) {
                if (data != null) if (data.data != null) {
                    uriImage = data.data
                    loadImageFromUrl(binding.imageView10, uriImage.toString())
                }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}