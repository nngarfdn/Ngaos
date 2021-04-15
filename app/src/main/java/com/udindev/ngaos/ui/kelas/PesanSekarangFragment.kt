package com.udindev.ngaos.ui.kelas

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.udindev.ngaos.R
import com.udindev.ngaos.databinding.FragmentPesanSekarangBinding

class PesanSekarangFragment : BottomSheetDialogFragment() {

    private lateinit var binding : FragmentPesanSekarangBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentPesanSekarangBinding.inflate(inflater, container, false)

        binding.btnBergabung.setOnClickListener{startActivity(Intent(context, PembayaranActivity::class.java))}
        return binding.root
    }



}


