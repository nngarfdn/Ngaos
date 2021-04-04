package com.udindev.ngaos.ui.profile

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.udindev.ngaos.databinding.ActivityProfileBinding
import com.udindev.ngaos.ui.auth.base.LoggedViewModelFactory
import com.udindev.ngaos.ui.auth.main.view.LoginActivity
import com.udindev.ngaos.ui.auth.main.viewmodel.LoggedInViewModel

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding : ActivityProfileBinding
    private lateinit var loggedInViewModel: LoggedInViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        setupViewModel()
        setupAuthObservers()

        binding.btnLogout.setOnClickListener {

            AlertDialog.Builder(this)
                    .setTitle("Keluar akun")
                    .setMessage("Apakah kamu yakin ingin keluar?")
                    .setNegativeButton("Tidak", null)
                    .setPositiveButton("Ya") { dialogInterface: DialogInterface?, i: Int ->
                        loggedInViewModel.logOut()
                        startActivity(Intent(this, LoginActivity::class.java))
                    }
                    .create().show()

        }
    }

    private fun setupViewModel() {
        loggedInViewModel = ViewModelProviders.of(
                this,
                LoggedViewModelFactory(this.application)
        ).get(LoggedInViewModel::class.java)
    }

    private fun setupAuthObservers() {
        loggedInViewModel.userLiveData.observe(this,
                { firebaseUser ->
                    if (firebaseUser != null) {
                        Log.d("DashboardActivity", "setupAuthObservers: ${firebaseUser.displayName}")
                        binding.txtNama.text = firebaseUser.displayName
                        binding.txtEmail.text = firebaseUser.email
                    }
                })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onStart() {
        super.onStart()
        setupAuthObservers()
    }

    override fun onResume() {
        super.onResume()
        setupAuthObservers()
    }
}