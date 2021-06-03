package com.udindev.ngaos.ui.kelas

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.udindev.ngaos.databinding.ActivityTerimakasihBinding
import com.udindev.ngaos.ui.dashboard.main.view.DashboardActivity

class TerimakasihActivity : AppCompatActivity() {

    private lateinit var binding : ActivityTerimakasihBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTerimakasihBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnOke.setOnClickListener {
            startActivity(Intent(this, DashboardActivity::class.java))
        }
    }
}