package com.udindev.ngaos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.udindev.ngaos.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnWaktuSholat.setOnClickListener {
            val i = Intent(this, WaktuSholatActivity::class.java)
            startActivity(i)
        }

        binding.btnNotif.setOnClickListener {
            val i = Intent(this, NotificationActivity::class.java)
            startActivity(i)
        }
    }
}