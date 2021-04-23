package com.udindev.ngaos.ui.auth.main.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.udindev.ngaos.databinding.FragmentDialogResetPaswordBinding
import com.udindev.ngaos.ui.auth.main.viewmodel.LoginRegisterViewModel

class DialogResetPasword : DialogFragment() {

    private lateinit var binding : FragmentDialogResetPaswordBinding
    private lateinit var loginRegisterViewModel: LoginRegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDialogResetPaswordBinding.inflate(inflater, container, false)

        loginRegisterViewModel = ViewModelProvider(this)[LoginRegisterViewModel::class.java]

        binding.imgClose.setOnClickListener { dialog?.dismiss() }
        binding.btnMasuk.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            if (email.length > 0 ) {
                loginRegisterViewModel.sendPasswordReset(email)
            } else {
                Toast.makeText(context, "Pastikan email terisi", Toast.LENGTH_SHORT).show()
            }
        }
        return binding.root
    }

}