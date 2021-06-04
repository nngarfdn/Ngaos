package com.udindev.ngaos.ui.kelas

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.udindev.ngaos.data.response.kelas.Data
import com.udindev.ngaos.databinding.FragmentPesanSekarangBinding
import com.udindev.ngaos.utils.AppUtils.loadImageFromUrl

class PesanSekarangFragment : BottomSheetDialogFragment() {

    private lateinit var binding : FragmentPesanSekarangBinding
    private var kelas : Data? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentPesanSekarangBinding.inflate(inflater, container, false)
        kelas = arguments?.getParcelable(DetailActivity.EXTRA_KELAS)
        binding.txtNamaKelLanjutan.text = kelas?.namaKelas
        binding.txtHargaKelasLanj.text = kelas?.biaya.toString()
        loadImageFromUrl(binding.imgKelas, kelas?.fotoKelas)

        binding.btnBergabung.setOnClickListener{
            val i = Intent(context, PembayaranActivity::class.java)
            i.putExtra(DetailActivity.EXTRA_KELAS, kelas)
            startActivity(i)}
        binding.imageView8.setOnClickListener { dismiss() }
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

}


