package com.udindev.ngaos.ui.kelas

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.udindev.ngaos.data.response.diikuti.Kela
import com.udindev.ngaos.databinding.ActivityDetailKelasDiikutiBinding
import com.udindev.ngaos.utils.AppUtils


class DetailKelasDiikuti : AppCompatActivity() {

    private lateinit var binding: ActivityDetailKelasDiikutiBinding
    private lateinit var kelas: Kela

    companion object{
        const val EXTRA_KELAS = "diikuti"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailKelasDiikutiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val i = intent.extras
        if (i != null) {
            kelas = i.getParcelable(EXTRA_KELAS)!!
        }
        binding.btnBergabung.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setData(Uri.parse(kelas.linkGrupWa));
            intent.setPackage("com.whatsapp");
            startActivity(intent);
        }
        initViews()
    }

    private fun initViews() {
        binding.txtNamaKelas.text = kelas.namaKelas
//        binding.txtNamaPengajar.text = kelas.pengajar
        AppUtils.loadImageFromUrl(binding.imgDetailKelas, kelas.fotoKelas)
        binding.txtDescription.text = kelas.deskripsiKelas
        binding.txtHargaKelas.text = kelas.biaya.toString()
        binding.txtWaktuPelaksanaan.text = "${kelas.waktuMulai} - ${kelas.waktuSelesai}"

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}