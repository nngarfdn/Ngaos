package com.udindev.ngaos.ui.kelas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.udindev.ngaos.R
import com.udindev.ngaos.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBergabung.setOnClickListener{
            val bottomSheet = PesanSekarangFragment()
            bottomSheet.show(supportFragmentManager, bottomSheet.tag)
        }
    }
}