package com.udindev.ngaos.ui.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.udindev.ngaos.databinding.ActivityLoginBinding
import com.udindev.ngaos.ui.auth.base.LoggedViewModelFactory
import com.udindev.ngaos.ui.auth.base.LoginRegisterViewModelFactory
import com.udindev.ngaos.ui.auth.viewmodel.LoggedInViewModel
import com.udindev.ngaos.ui.auth.viewmodel.LoginRegisterViewModel

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnDaftar.setOnClickListener { v: View? ->
            val i = Intent(this, RegisterActivity::class.java)
            startActivity(i)
        }


    }
}