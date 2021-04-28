package com.udindev.ngaos.ui.kelas

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import com.udindev.ngaos.utils.AppUtils.loadImageFromUrl
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.udindev.ngaos.data.model.Kelas
import com.udindev.ngaos.databinding.FragmentPesanSekarangBinding

class PesanSekarangFragment : BottomSheetDialogFragment() {

    private lateinit var binding : FragmentPesanSekarangBinding
    private var kelas : Kelas? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentPesanSekarangBinding.inflate(inflater, container, false)


        kelas = arguments?.getParcelable(DetailActivity.EXTRA_KELAS)
        binding.txtNamaKelLanjutan.text = kelas?.namaKelas
        loadImageFromUrl(binding.imgKelas, kelas?.photo)

        binding.btnBergabung.setOnClickListener{startActivity(Intent(context, PembayaranActivity::class.java))}
        binding.imageView8.setOnClickListener { dismiss() }
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

}


