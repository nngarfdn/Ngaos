package com.udindev.ngaos.ui.dashboard.main.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.nanang.retrocoro.ui.base.ViewModelFactory
import com.udindev.ngaos.api.ApiHelper
import com.udindev.ngaos.api.RetrofitBuilder
import com.udindev.ngaos.data.response.diikuti.Kela
import com.udindev.ngaos.databinding.FragmentKelasDiikutiBinding
import com.udindev.ngaos.ui.dashboard.main.adapter.DaftarDiikutiAdapter
import com.udindev.ngaos.utils.Status

class KelasDiikutiFragment : Fragment() {

    private lateinit var binding : FragmentKelasDiikutiBinding
    private lateinit var adapter : DaftarDiikutiAdapter
    private lateinit var viewModel: KelasViewModel
    private var firebaseAuth: FirebaseAuth? = null
    private var firebaseUser: FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentKelasDiikutiBinding.inflate(inflater, container, false)

        firebaseAuth = FirebaseAuth.getInstance()
        firebaseUser = firebaseAuth!!.currentUser
        binding.rvDaftarKelas.layoutManager = LinearLayoutManager(context)
        binding.rvDaftarKelas.setHasFixedSize(true)
        adapter = DaftarDiikutiAdapter(requireActivity())
        adapter.notifyDataSetChanged()
        binding.rvDaftarKelas.adapter = adapter
        setupViewModel()
        setupObservers()

        return binding.root

    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(
            this, ViewModelFactory(ApiHelper(RetrofitBuilder.apiServiceDiikuti))
        ).get(KelasViewModel::class.java)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setupObservers() {
        firebaseUser?.uid?.let {
            viewModel.getKelasDiikuti(it).observe(viewLifecycleOwner, {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            resource.data?.let { kelas ->
                                binding.progressBar.visibility = View.INVISIBLE
                                adapter = DaftarDiikutiAdapter(requireActivity())
                                adapter.notifyDataSetChanged()
                                adapter.data = kelas.data?.kelas as java.util.ArrayList<Kela>?
                                binding.rvDaftarKelas.adapter = adapter

                                if (adapter.data?.isEmpty() == true){
                                    binding.progressBar.setAnimation("emot.json")
                                    binding.progressBar.visibility = View.VISIBLE
                                }
                            }
                        }
                        Status.ERROR -> {
                            binding.progressBar.visibility = View.INVISIBLE
                            Log.d("EROR", "setupObservers: ${it.message}")
                        }
                        Status.LOADING -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }
                    }
                }
            })
        }
    }


}