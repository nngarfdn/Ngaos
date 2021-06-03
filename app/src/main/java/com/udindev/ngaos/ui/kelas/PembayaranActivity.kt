package com.udindev.ngaos.ui.kelas

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.nanang.retrocoro.ui.base.ViewModelFactory
import com.udindev.ngaos.R
import com.udindev.ngaos.api.ApiHelper
import com.udindev.ngaos.api.RetrofitBuilder
import com.udindev.ngaos.callback.OnImageUploadCallback
import com.udindev.ngaos.databinding.ActivityPembayaranBinding
import com.udindev.ngaos.utils.AppUtils.loadImageFromUrl
import com.udindev.ngaos.utils.costumview.LoadingDialog
import java.util.*

class PembayaranActivity : AppCompatActivity() {

    companion object{
        private const val RC_PICK_IMAGE = 100
    }

    private var uriImage: Uri? = null
    private var firebaseAuth: FirebaseAuth? = null
    private var firebaseUser: FirebaseUser? = null
    private var loadingDialog: LoadingDialog? = null
    private lateinit var binding : ActivityPembayaranBinding
    private lateinit var viewModel: PembayaranViewModel

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

        setupViewModel()

        binding.btnPilihGambar.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(Intent.createChooser(intent, "Unggah foto"), RC_PICK_IMAGE)
        }

        binding.btnBergabung.setOnClickListener {
            val id = firebaseUser?.uid
            val fileName: String = binding.radioGroup1.checkedRadioButtonId.toString() + Calendar.getInstance().time + ".jpeg"
            if (uriImage == null) {
                Toast.makeText(this, "Masukan Bukti Pembayaran", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }



            viewModel.uploadImage(this, id, uriImage, fileName, object : OnImageUploadCallback{
                override fun onSuccess(imageUrl: String?) {
                    Log.d("Upload Image", "onSuccess: $imageUrl")
                    Toast.makeText(applicationContext, "Berhasil", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(applicationContext, TerimakasihActivity::class.java))
                }
            })
        }

        binding.txtPetunjukBayar.text =
            "Petunjuk Penbayaran Gopay \n" +
                    "1. Klik Bayar dengan GO-PAY. \n" +
                    "2. Aplikasi GO-JEK akan terbuka secara otomatis untuk melakukan pembayaran. \n" +
                    "3. Periksa rincian pembayaran Anda lalu klik Pay dan transaksi Anda selesai."

        setupPetuntukPembayaran()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(
            this, ViewModelFactory(ApiHelper(RetrofitBuilder.apiServiceKelas))
        ).get(PembayaranViewModel::class.java)
    }

    @SuppressLint("SetTextI18n")
    private fun setupPetuntukPembayaran() {
        binding.radioGroup1.setOnCheckedChangeListener { _, id ->
            if (id == R.id.radioSubuh) binding.txtPetunjukBayar.text =
                "Petunjuk Penbayaran Gopay \n" +
                        "1. Klik Bayar dengan GO-PAY. \n" +
                        "2. Aplikasi GO-JEK akan terbuka secara otomatis untuk melakukan pembayaran. \n" +
                        "3. Periksa rincian pembayaran Anda lalu klik Pay dan transaksi Anda selesai."
            if (id == R.id.radioDzuhur) binding.txtPetunjukBayar.text =
                "Petunjuk Penbayaran OVO \n" +
                        "1. Masukkan nomor HP Anda yang terdaftar di OVO. \n" +
                        "2. Tekan notifikasi OVO yang akan muncul setelah memasukkan no. HP Anda di atas. \n" +
                        "3. Periksa rincian pembayaran. \n"
                        "4. Selesaikan pembayaran di aplikasi OVO."
            if (id == R.id.radioAshar) binding.txtPetunjukBayar.text =
                "Petunjuk Penbayaran Bank Mandiri \n" +
                        "1. Pada menu utama, pilih Bayar/Beli. \n" +
                        "2. Pilih Lainnya. \n" +
                        "3. Pilih Multi Payment. \n" +
                        "4. Masukkan 70012 (kode preusahaan Midtrans) lalu tekan Benar. \n" +
                        "5. Masukkan kode pembayaran Anda lalu tekan Benar. \n" +
                        "6. Pada halaman konfirmasi akan muncul detail pembayaran Anda. Jika informasi telah sesuai tekan Ya."
            if (id == R.id.radioBCA) binding.txtPetunjukBayar.text =
                "Petunjuk Penbayaran Bank BCA \n" +
                        "1. Masukkan kartu, pilih bahasa, dan masukkan PIN sesuai petunjuk di layar. \n" +
                        "2. Pada menu utama, pilih Transaksi Lainnya. \n" +
                        "3. Pilih Transfer dan pilih ke rekening BCA. \n" +
                        "4. Masukkan nominal transfer sesuai dengan total tagihan transaksi di aplikasi Ngaos. \n" +
                        "5.\tMasukkan nomor rekening BCA yang tertera di aplikasi Ngaos sebagai tujuan transfer."
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