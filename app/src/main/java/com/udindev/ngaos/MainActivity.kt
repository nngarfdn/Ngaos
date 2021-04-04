package com.udindev.ngaos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.udindev.ngaos.databinding.ActivityMainBinding
import com.udindev.ngaos.ui.auth.main.view.LoginActivity
import com.udindev.ngaos.ui.dashboard.main.view.DashboardActivity


class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnNotif.setOnClickListener {
            val i = Intent(this, NotificationActivity::class.java)
            startActivity(i)
        }

        binding.btnLogin.setOnClickListener {
            val i = Intent(this, LoginActivity::class.java)
            startActivity(i)
        }

        binding.btnDashboard.setOnClickListener {
            val i = Intent(this, DashboardActivity::class.java)
            startActivity(i)
        }


    }
}