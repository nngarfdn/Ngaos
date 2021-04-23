package com.udindev.ngaos.ui.auth.main.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.udindev.ngaos.R
import com.udindev.ngaos.databinding.ActivityLoginBinding
import com.udindev.ngaos.ui.auth.base.LoginRegisterViewModelFactory
import com.udindev.ngaos.ui.auth.main.viewmodel.LoginRegisterViewModel
import com.udindev.ngaos.ui.dashboard.main.view.DashboardActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginRegisterViewModel: LoginRegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loginRegisterViewModel = ViewModelProviders.of(this, LoginRegisterViewModelFactory(this.application))
                .get(LoginRegisterViewModel::class.java)


        loginRegisterViewModel.userLiveData.observe(this, { firebaseUser ->
            if (firebaseUser != null) startActivity(Intent(this,DashboardActivity::class.java))
        })
        binding.btnDaftar.setOnClickListener{ startActivity(Intent(this, RegisterActivity::class.java)) }
        binding.btnMasuk.setOnClickListener { v: View? ->
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            if (email.length > 0 && password.length > 0) {
                loginRegisterViewModel.login(email, password)
            } else {
                Toast.makeText(this, "Pastikan semua data sudah lengkap", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnLupakatasandi.setOnClickListener {
            val dialog = DialogResetPasword()
            dialog.show(supportFragmentManager,"ResetPassword")
        }


    }

}