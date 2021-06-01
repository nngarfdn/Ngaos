package com.udindev.ngaos.ui.kelas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.udindev.ngaos.data.response.kelas.Data
import com.udindev.ngaos.utils.AppUtils.loadImageFromUrl
import com.udindev.ngaos.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_KELAS = "kelas"
    }
    private lateinit var binding : ActivityDetailBinding
    private lateinit var kelas : Data

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        val i = intent.extras
        if (i != null){
            kelas = i.getParcelable(EXTRA_KELAS)!!
        }
        binding.btnBergabung.setOnClickListener{
            val bottomSheet = PesanSekarangFragment()
            val bundle = Bundle()
            bundle.putParcelable(EXTRA_KELAS, kelas)
            bottomSheet.arguments = bundle
            bottomSheet.show(supportFragmentManager, bottomSheet.tag)
        }
        initViews()
    }

    private fun initViews() {
        binding.txtNamaKelas.text = kelas.namaKelas
//        binding.txtNamaPengajar.text = kelas.pengajar
        loadImageFromUrl(binding.imgDetailKelas, kelas.fotoKelas)
        binding.txtDescription.text = kelas.deskripsiKelas
        binding.txtHargaKelas.text = kelas.biaya.toString()
        binding.txtWaktuPelaksanaan.text = "${kelas.waktuMulai} - ${kelas.waktuSelesai}"

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}