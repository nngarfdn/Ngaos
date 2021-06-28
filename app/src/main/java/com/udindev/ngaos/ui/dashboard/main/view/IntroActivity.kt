package com.udindev.ngaos.ui.dashboard.main.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.udindev.ngaos.R
import com.udindev.ngaos.ui.auth.main.view.LoginActivity

class IntroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        var btn_intro = findViewById(R.id.btn_mulai) as Button

        btn_intro.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent);
        }
    }
}